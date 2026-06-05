package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.ServerController;
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

    public RMIServerInterface(ClientController clientController, VirtualServer wrappedServer) {
        super(clientController);
        this.wrappedServer = wrappedServer;
    }

    @Override
    public void registerClient(ClientInterface client) {
        submitRemoteCall(
                () -> wrappedServer.registerClient(client)
        );
    }

    @Override
    public void login(String clientID, String username) {
        submitRemoteCall(
                () -> wrappedServer.login(clientID, username)
        );
    }

    @Override
    public void createLobby(String clientID, int playerNum, Totem totem) {
        submitRemoteCall(
                () -> wrappedServer.createLobby(clientID, playerNum, totem)
        );
    }

    @Override
    public void joinLobby(String clientID, int lobbyID, Totem totem) {
        submitRemoteCall(
                () -> wrappedServer.joinLobby(clientID, lobbyID, totem)
        );
    }

    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        submitRemoteCall(
                () -> wrappedServer.leaveLobby(clientID, lobbyID)
        );
    }

    @Override
    public void startLobby(String clientID, int lobbyID) {
        submitRemoteCall(
                () -> wrappedServer.startLobby(clientID, lobbyID)
        );
    }

    @Override
    public void getLobbyInfo(String clientID, int lobbyID) {
        submitRemoteCall(
                () -> wrappedServer.getLobbyInfo(clientID, lobbyID)
        );
    }

    @Override
    public void getLeaderboard(String clientID, int playerNum) {
        submitRemoteCall(
                () -> wrappedServer.getLeaderboard(clientID, playerNum)
        );
    }

    @Override
    public void requestAction(String clientID, int lobbyID, PlayerAction action) {
        submitRemoteCall(
                () -> wrappedServer.requestAction(clientID, lobbyID, action)
        );
    }

    @Override
    public void ping(String clientID) {
        submitRemoteCall(
                () -> wrappedServer.ping(clientID)
        );
    }

    @Override
    public void disconnect() {
        isConnected = false;
        try {
            UnicastRemoteObject.unexportObject(clientController, true);
        } catch (RemoteException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
        }
    }

    @FunctionalInterface
    interface RunnableChecked {
        void run() throws RemoteException;
    }

    /**Method to call remote methods. Used to manage RemoteException.
     * The methods that call this pass a lambda expression containing the remote call without having to catch the exception.
     * @param remoteCall functional interface that runs a given method and throws RemoteException-
     * */
    private void submitRemoteCall(RunnableChecked remoteCall) {
            if (isConnected) {
                try {
                    remoteCall.run();
                } catch (RemoteException e) {
                    clientController.disconnect();
                }
            }
        }
}
