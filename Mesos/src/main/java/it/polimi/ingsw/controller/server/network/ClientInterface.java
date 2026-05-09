package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.List;
import java.util.Map;

public abstract class ClientInterface implements VirtualClient {
    protected int id = 0;
    protected LobbyController currLobbyController = null;
    protected boolean isConnected = false;


    public abstract void showWaitingLobbies(int clientID, List<Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players);
    public abstract void addClient(int clientID, int lobbyID, Player player);
    public abstract void addPlayer(int clientID, int lobbyID, Player player);
    public abstract void removeClient(int clientID, int lobbyID, Player player);
    public abstract void removePlayer(int clientID, int lobbyID, Player player);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void updateState(int clientID, int lobbyID, ModelStateInfo modelStateInfo);
    public abstract void createLobby(int clientID, Lobby lobby, Player player);
    public abstract void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes);
    public abstract void stopLobby(int clientID, int lobbyID);
    public abstract void ping();
    public void cleanup() {};
    public abstract void showError(int clientID, String errorMessage);

    public abstract void updateModel(int clientID, int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack);
    public abstract void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Board board);
    public abstract void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Row topRow);
    public abstract void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Row topRow, Row bottomRow);
    public abstract void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Map<Integer, Integer> ranking);



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

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String toString() {
        return "[Client " + id + "]";
    }
}
