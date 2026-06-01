package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LeaderboardDB {
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "leaderboard";
    private static final String username = "root";
    private static final String password = "";

    private ExecutorService dbService;
    private Lock readLock;
    private Lock writeLock;

    public LeaderboardDB() {
        if (createDatabaseIfNotExists() && createTablesIfNotExist()) {
            dbService = Executors.newFixedThreadPool(5);

            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        }
    }

    private boolean createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            return true;
        } catch (SQLException ex) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Error creating database: " + ex.getMessage());
            return false;
        }
    }

    private boolean createTablesIfNotExist() {
        try (Connection conn = DriverManager.getConnection(url + dbName, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS game_results(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nickname  VARCHAR(30) NOT NULL,
                        pp INT NOT NULL,
                        food INT NOT NULL,
                        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        lobby_size INT NOT NULL
                    )
                """);
            return true;
        } catch (SQLException ex) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Error creating tables: " + ex.getMessage());
            return false;
        }
    }


    public void saveResults(Game game) {
        dbService.submit(() -> {
            writeLock.lock();
            try {
                for (Player p : game.getPlayers()) {
                    String nickname = p.getName();
                    int pp = p.getPP();
                    int food = p.getFood();

                    String s = "INSERT INTO game_results(nickname, pp, food, lobby_size) VALUES (?, ?, ?, ?)";

                    try (Connection conn = DriverManager.getConnection(url + dbName, username, password);
                         PreparedStatement ps = conn.prepareStatement(s)) {

                        ps.setString(1, nickname);
                        ps.setInt(2, pp);
                        ps.setInt(3, food);
                        ps.setInt(4, game.getPlayers().size());

                        ps.executeUpdate();

                    } catch (SQLException ex) {
                        Logger.getInstance().print(LoggerLevel.ERROR, "Error saving result: " + ex.getMessage());
                    }
                }
            } finally {
                writeLock.unlock();
            }
        });

    }


    /**
     * This method returns a LeaderboardResult object containing the list of every entry pertaining a certain lobby size,
     * along with an integer that indicates the position of the client who requested it.
     * Since the database includes every completed game and a client might have played multiple, it returns the highest ranking.
     *
     * @param client    The client's id corresponds to their nickname.
     * @param playerNum The lobby's size (number of players).
     *
     */
    public void getLeaderboard(ClientInterface client, int playerNum) {
        dbService.submit(() -> {
            List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
            Date maxDate = new Date(0);
            int entryID = -1;

            String query = "SELECT id, nickname, pp, food, date FROM game_results WHERE lobby_size = (?) ORDER BY pp DESC, food DESC";

            readLock.lock();
            try {
                try (Connection conn = DriverManager.getConnection(url + dbName, username, password);
                     PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, playerNum);

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String nickname = rs.getString("nickname");
                            Date date = rs.getTimestamp("date");
                            leaderboardEntries.add(new LeaderboardEntry(
                                id, nickname, rs.getInt("pp"),
                                rs.getInt("food"), date));

                            if (nickname.equals(client.getID()) && date.after(maxDate)) {
                                entryID = id;
                                maxDate = date;
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getInstance().print(LoggerLevel.ERROR, "Error fetching result set: " + ex.getMessage());
                    }
                } catch (SQLException ex) {
                    Logger.getInstance().print(LoggerLevel.ERROR, "Error getting leaderboard: " + ex.getMessage());
                }
            } finally {
                readLock.unlock();
                client.showLeaderboard(new LeaderboardResult(leaderboardEntries, entryID));
            }
        });
    }

    public boolean isAvailable() {
        return dbService != null;
    }
}
