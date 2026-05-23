package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDatabase {
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "leaderboard";
    private static final String username = "root";
    private static final String password = "";

    public LeaderboardDatabase(){
        createDatabaseIfNotExists();
        createTablesIfNotExist();
    }


    private static void createDatabaseIfNotExists() {
        try(Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement()){
            stmt.execute("CREATE DATABASE IF NOT EXISTS " +  dbName);
        } catch (SQLException ex) {
            System.err.println("Error creating database " + ex.getMessage());
        }
    }

    private static void createTablesIfNotExist(){
        try(Connection conn = DriverManager.getConnection(url + dbName, username, password);
        Statement stmt = conn.createStatement()){

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

        } catch (SQLException ex) {
            System.err.println("Error creating tables " + ex.getMessage());
        }
    }


    public static void saveResults(Game game){
        for(Player p: game.getPlayers()){
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
                System.err.println("Error saving results " + ex.getMessage());
            }
        }
    }


    /** This method returns a LeaderboardResult object containing the list of every entry pertaining a certain lobby size,
     * along with an integer that indicates the position of the client who requested it.
     * Since the database includes every completed game and a client might have played multiple, it returns the highest ranking.
     * @param clientID The client's id corresponds to their nickname.
     * @param playerNum The lobby's size (number of players).
     * */
    public static LeaderboardResult getLeaderboard(String clientID, int playerNum) {
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        int rank = -1;

        String query = "SELECT nickname, pp, food, date FROM game_results WHERE lobby_size = (?) ORDER BY pp DESC, food DESC";

        try(Connection conn = DriverManager.getConnection(url + dbName, username, password);
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, playerNum);

            try (ResultSet rs = ps.executeQuery()) {

                int index = 1;

                while (rs.next()) {
                    leaderboardEntries.add(new LeaderboardEntry(
                            rs.getString("nickname"), rs.getInt("pp"),
                            rs.getInt("food"), rs.getString("date")));
                    if (rank == -1 && rs.getString("nickname").equals(clientID)) {
                        rank = index;
                    }
                    index++;
                }

            } catch (SQLException ex) {
                System.err.println("Error fetching result set " + ex.getMessage());
            }

        } catch  (SQLException ex) {
            System.err.println("Error getting leaderboard " + ex.getMessage());
        }

        return new LeaderboardResult(leaderboardEntries, rank);
    }

}
