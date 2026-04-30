package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RMIClientInterface extends ClientInterface {
    VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public void setID(int clientID)  {
        this.id = clientID;

        try {
            wrappedClient.setID(clientID);
        } catch (IOException e) {

        }
    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies)  {
        try {
            wrappedClient.setWaitingLobbies(clientID, lobbies);
        } catch (IOException e) {

        }
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players)  {
        try {
            wrappedClient.showLobbyInfo(clientID, lobbyID, players);
        } catch (IOException e) {

        }
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player)  {
        try {
            wrappedClient.setLobby(clientID, lobbyID, player);
        } catch (IOException e) {

        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID)  {
        try {
            wrappedClient.removeFromLobby(clientID, lobbyID);
        } catch (IOException e) {

        }
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings)  {
        try {
            wrappedClient.showRank(clientID, lobbyID, rankings);
        } catch (IOException e) {

        }
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard)  {
        try {
            wrappedClient.showLeaderboard(clientID, leaderboard);
        } catch (IOException e) {

        }
    }

    @Override
    public void confirmPick(int clientID, Board board, Tribe updatedTribe)  {
        try {
            wrappedClient.confirmPick(clientID, board, updatedTribe);
        } catch (IOException e) {

        }
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player)  {
        try {
            wrappedClient.createLobby(clientID, lobby, player);
        } catch (IOException e) {

        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes)  {
        try {
            wrappedClient.startLobby(clientID, lobbyID, board, tribes);
        } catch (IOException e) {

        }
    }

    @Override
    public void stopLobby(int lobbyID)  {
        try {
            wrappedClient.stopLobby(lobbyID);
        } catch (IOException e) {

        }
    }

    @Override
    public void deleteLobby(int lobbyID)  {
        try {
            wrappedClient.deleteLobby(lobbyID);
        } catch (IOException e) {

        }
    }

    @Override
    public void ping() throws IOException {
        wrappedClient.ping();
    }
}
