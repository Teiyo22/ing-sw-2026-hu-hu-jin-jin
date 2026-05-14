package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class ClientInterface implements VirtualClient, Serializable {
    protected String id;
    protected LobbyController currLobbyController = null;
    protected boolean isConnected = false;

    public abstract void confirmLogin(String username);
    public abstract void showWaitingLobbies(List<Lobby> lobbies);

    public abstract void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers);
    public abstract void addPlayer(int lobbyID, Player player);
    public abstract void addLobby(Lobby lobby);
    public abstract void removeLobby(int lobbyID);
    public abstract void updateLobby(Lobby lobby);
    public abstract void removeClient(int lobbyID, Player player);
    public abstract void removePlayer(int lobbyID, Player player);
    public abstract void showLeaderboard(List<LeaderboardEntry> leaderboard);
    public abstract void updateState(int lobbyID, ModelStateInfo modelStateInfo);
    public abstract void createLobby(Lobby lobby, Player player);
    public abstract void startLobby(int lobbyID, Board board, List<Player> players);
    public abstract void stopLobby(int lobbyID);
    public abstract void showError(String errorMessage);
    public abstract void ping();

    public abstract void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack);
    public abstract void updateModel(int lobbyID, Player player, Board board);
    public abstract void updateModel(int lobbyID, Player player, Row topRow);
    public abstract void updateModel(int lobbyID, List<Player> players, Row topRow, Row bottomRow);
    public abstract void updateModel(int lobbyID, List<Player> players);

    public void cleanup() {};


    public String getID() {
        return id;
    }

    public void setID(String clientID) {
        this.id = clientID;
    }

    public LobbyController getCurrLobbyController() {
        return currLobbyController;
    }

    public void setCurrLobbyController(LobbyController lobbyController) {
        if (currLobbyController != null)
            currLobbyController.getListeners().remove(this);

        lobbyController.getListeners().add(this);
        currLobbyController = lobbyController;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    @Override
    public String toString() {
        return "[Client " + id + "]";
    }
}
