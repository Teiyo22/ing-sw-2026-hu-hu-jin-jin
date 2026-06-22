package it.polimi.ingsw.utils.leaderboard;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LeaderboardDB {
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "leaderboard";
    private static final String username = "mesos";
    private static final String password = "";

    private ExecutorService dbService;
    private Lock readLock;
    private Lock writeLock;

    public LeaderboardDB() {
        if (createDatabaseIfNotExists() && createTablesIfNotExist()) {
            dbService = Executors.newVirtualThreadPerTaskExecutor();

            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        }
    }

    private boolean createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            Logger.getInstance().print(LoggerLevel.SERVER, "LeaderboardDB: Successfully added new database");
            return true;
        } catch (SQLException ex) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Error creating database: " + ex.getMessage());
            return false;
        }
    }

    private boolean createTablesIfNotExist() {
        try (Connection conn = DriverManager.getConnection(url + dbName, username, password);
             Statement stmt = conn.createStatement()) {

            for(int i = 2; i <= 5; i++) {
                String sql_stat = String.format(
                    """
                    CREATE TABLE IF NOT EXISTS results_%dp(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nickname  VARCHAR(30) NOT NULL,
                        pp INT NOT NULL,
                        food INT NOT NULL,
                        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)
                    """,
                    i);

                stmt.executeUpdate(sql_stat);
            }
            Logger.getInstance().print(LoggerLevel.SERVER, "LeaderboardDB: successfully tables created");
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

                    String s = String.format("INSERT INTO results_%dp(nickname, pp, food) VALUES (?, ?, ?, ?)", game.getPlayerConfig().getNum());

                    try (Connection conn = DriverManager.getConnection(url + dbName, username, password);
                         PreparedStatement ps = conn.prepareStatement(s)) {

                        ps.setString(1, nickname);
                        ps.setInt(2, pp);
                        ps.setInt(3, food);

                        ps.executeUpdate();
                        Logger.getInstance().print(LoggerLevel.SERVER, "LeaderboardDB: Successfully added new entry to leaderboard");
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

            String query =
                String.format("SELECT id, nickname, pp, food, date FROM results_%dp ORDER BY pp DESC, food DESC",
                    playerNum);

            readLock.lock();
            try {
                try (Connection conn = DriverManager.getConnection(url + dbName, username, password);
                     PreparedStatement ps = conn.prepareStatement(query)) {
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

    public void close() {
        if (dbService == null) return;

        dbService.shutdown();

        try {
            if (!dbService.awaitTermination(5, TimeUnit.SECONDS)) {
                dbService.shutdownNow();
            }
        } catch (InterruptedException e) {
            dbService.shutdownNow();
        }
    }
}
