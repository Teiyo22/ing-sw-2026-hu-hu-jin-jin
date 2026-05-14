package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.concurrent.*;

public class RMIServerInterface extends ServerInterface {
    VirtualServer wrappedServer;

    private final ScheduledExecutorService retryScheduler = Executors.newScheduledThreadPool(1);
    private final Long delay = 3L;

    public RMIServerInterface(ClientController clientController, VirtualServer wrappedServer) {
        super(clientController);
        this.wrappedServer = wrappedServer;
    }

    @Override
    public void registerClient(ClientInterface client) {
        try {
            wrappedServer.registerClient(client);
        } catch (RemoteException e) {
            Logger.getInstance().print(LoggerLevel.DEBUG, e.getMessage());
            reschedule(() -> registerClient(client));
        }
    }

    @Override
    public void login(String clientID, String username) {
        try {
            wrappedServer.login(clientID, username);
        } catch (RemoteException e) {
            reschedule(() -> {
                login(clientID, username);});
        }
    }

    @Override
    public void createLobby(String clientID, int playerNum, Totem totem) {
        try {
            wrappedServer.createLobby(clientID, playerNum, totem);
        } catch (RemoteException e) {
            reschedule(() -> {createLobby(clientID, playerNum, totem);});
        }
    }

    @Override
    public void joinLobby(String clientID, int lobbyID, Totem totem) {
        try {
            wrappedServer.joinLobby(clientID, lobbyID, totem);
        } catch (RemoteException e) {
            reschedule(() -> {joinLobby(clientID, lobbyID, totem);});
        }
    }

    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        try {
            wrappedServer.leaveLobby(clientID, lobbyID);
        } catch (RemoteException e) {
            reschedule(() -> {leaveLobby(clientID, lobbyID);});
        }
    }

    @Override
    public void startLobby(String clientID, int lobbyID) {
        try {
            wrappedServer.startLobby(clientID, lobbyID);
        } catch (RemoteException e) {
            reschedule(() -> {startLobby(clientID, lobbyID);});
        }
    }

    @Override
    public void getLobbyInfo(String clientID, int lobbyID) {
        try {
            wrappedServer.getLobbyInfo(clientID, lobbyID);
        } catch (RemoteException e) {
            reschedule(() -> {getLobbyInfo(clientID, lobbyID);});
        }
    }

    @Override
    public void getLeaderboard(String clientID, int playerNum) {
        try {
            wrappedServer.getLeaderboard(clientID, playerNum);
        } catch (RemoteException e) {
            reschedule(() -> {getLeaderboard(clientID, playerNum);});
        }
    }

    @Override
    public void requestAction(String clientID, int lobbyID, PlayerAction action) {
        try {
            wrappedServer.requestAction(clientID, lobbyID, action);
        } catch (RemoteException e) {
            reschedule(() -> {requestAction(clientID, lobbyID, action);});
        }
    }

    @Override
    public void ping(String clientID) {
        try {
            wrappedServer.ping(clientID);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void disconnect() {
        try {
            UnicastRemoteObject.unexportObject(clientController, true);
        } catch (RemoteException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
        }

        retryScheduler.shutdown();

        try {
            if (!retryScheduler.awaitTermination(10, TimeUnit.SECONDS))
                retryScheduler.shutdownNow();

        } catch (InterruptedException e) {
            retryScheduler.shutdownNow();
        }
    }

    private void reschedule (Runnable task) {
        retryScheduler.schedule(
                task,
                delay,
                TimeUnit.SECONDS
        );
    }
}
