package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class RMIClientInterface extends ClientInterface {
    VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public synchronized void setID(int clientID)  {
        if (!isConnected)
            return;

        this.id = clientID;

        try {
            wrappedClient.setID(clientID);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                setID(clientID);});
        }
    }

    @Override
    public synchronized void showWaitingLobbies(int clientID, List<Lobby> lobbies)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.showWaitingLobbies(clientID, lobbies);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                showWaitingLobbies(clientID, lobbies);});
        }
    }

    @Override
    public synchronized void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players)  {
        if (!isConnected)
            return;

        Logger.getInstance().print(LoggerLevel.DEBUG, "Setting current lobby controller to " + lobbyID);
        setCurrLobbyController(lobbyID);

        try {
            Logger.getInstance().print(LoggerLevel.DEBUG, "Calling remote method: showLobbyInfo");
            wrappedClient.showLobbyInfo(clientID, lobbyID, players);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                showLobbyInfo(clientID, lobbyID, players);});
        }
    }

    @Override
    public synchronized void addClient(int clientID, int lobbyID, Player player)  {
        if (!isConnected)
            return;

        try {
            Logger.getInstance().print(LoggerLevel.DEBUG, "Calling remote method: addClient");
            wrappedClient.addClient(clientID, lobbyID, player);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                addClient(clientID, lobbyID, player);});
        }
    }

    @Override
    public synchronized void addPlayer(int clientID, int lobbyID, Player player)  {
        if (!isConnected)
            return;

        try {
            Logger.getInstance().print(LoggerLevel.DEBUG, "Calling remote method: addPlayer");
            wrappedClient.addPlayer(clientID, lobbyID, player);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                addPlayer(clientID, lobbyID, player);});
        }
    }

    @Override
    public synchronized void removeClient(int clientID, int lobbyID, Player player)  {
        if (!isConnected)
            return;


        try {
            wrappedClient.removeClient(clientID, lobbyID, player);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                removeClient(clientID, lobbyID, player);});
        }
    }

    @Override
    public synchronized void removePlayer(int clientID, int lobbyID, Player player)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.removePlayer(clientID, lobbyID, player);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                removePlayer(clientID, lobbyID, player);});
        }
    }

    @Override
    public synchronized void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.showRank(clientID, lobbyID, rankings);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                showRank(clientID, lobbyID, rankings);});
        }
    }

    @Override
    public synchronized void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.showLeaderboard(clientID, leaderboard);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                showLeaderboard(clientID, leaderboard);});
        }
    }

    @Override
    public synchronized void updateState(int clientID, int lobbyID, ModelStateInfo modelStateInfo) {
        if (!isConnected)
            return;

        try {
            wrappedClient.updateState(clientID, lobbyID, modelStateInfo);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {updateState(clientID, lobbyID, modelStateInfo);});
        }
    }

    @Override
    public void updateModel(int clientID, int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.updateModel(clientID, lobbyID, orderTile, offerTrack);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                updateModel(clientID, lobbyID, orderTile, offerTrack);});
        }
    }

    public void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Board board) {
        if (!isConnected)
            return;

        try {
            wrappedClient.updateModel(clientID, lobbyID, player, tribe, board);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                updateModel(clientID, lobbyID, player, tribe, board);});
        }
    }

    @Override
    public synchronized void createLobby(int clientID, Lobby lobby, Player player)  {
        if (!isConnected)
            return;

        setCurrLobbyController(lobby.getLobbyID());

        try {
            wrappedClient.createLobby(clientID, lobby, player);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                createLobby(clientID, lobby, player);});
        }
    }

    @Override
    public synchronized void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes)  {
        if (!isConnected)
            return;

        try {
            Logger.getInstance().print(LoggerLevel.DEBUG, "Calling remote method: startLobby");
            wrappedClient.startLobby(clientID, lobbyID, board, tribes);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                startLobby(clientID, lobbyID, board, tribes);});
        }
    }

    @Override
    public synchronized void stopLobby(int clientID, int lobbyID)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.stopLobby(clientID, lobbyID);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                stopLobby(clientID, lobbyID);});
        }
    }

    @Override
    public synchronized void showError(int clientID, String errorMessage) {
        if (!isConnected)
            return;

        try {
            wrappedClient.showError(clientID, errorMessage);
        } catch (RemoteException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                showError(clientID, errorMessage);});
        }
    }

    @Override
    public void ping() {
        try {
            wrappedClient.ping();
        } catch (RemoteException e) {

        }
    }
}
