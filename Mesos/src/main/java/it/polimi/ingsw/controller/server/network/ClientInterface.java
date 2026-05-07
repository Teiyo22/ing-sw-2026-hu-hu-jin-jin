package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.List;
import java.util.Map;

public abstract class ClientInterface implements VirtualClient {
    protected int id = 0;
    protected LobbyController currLobbyController = null;
    protected boolean isConnected = false;


    public abstract void showWaitingLobbies(int clientID, List<Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Player, Integer> players);
    public abstract void addClient(int clientID, int lobbyID, Player player);
    public abstract void addPlayer(int clientID, int lobbyID, Player player);
    public abstract void removeClient(int clientID, int lobbyID, Player player);
    public abstract void removePlayer(int clientID, int lobbyID, Player player);
    public abstract void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void updateModel(int clientID, Board board, Tribe updatedTribe);
    public abstract void createLobby(int clientID, Lobby lobby, Player player);
    public abstract void startLobby(int clientID, int lobbyID, Board board, Map<Player, Tribe> tribes);
    public abstract void stopLobby(int clientID, int lobbyID);
    public abstract void ping();
    public void cleanup() {};
    public abstract void showError(int clientID, String errorMessage);


    public int getID() {
        return id;
    }

    public void setID(int clientID) {
        id = clientID;
    };

    public synchronized LobbyController getCurrLobbyController() {
        return currLobbyController;
    }

    public void setCurrLobbyController(int lobbyID) {
        if (currLobbyController != null)
            currLobbyController.getListeners().remove(this);

        LobbyController lobbyController = ServerController.getInstance().getLobbies().get(lobbyID);

        lobbyController.getListeners().add(this);
        currLobbyController = lobbyController;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
