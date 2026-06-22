package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.controller.client.network.ServerInterface;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

import java.io.Serializable;

/**
 * RMIServerService acts as the entry-point for the RMI remote method calls from the client to the server.
 * Each method essentially submits a task to an executor service to avoid long-lasting blocking calls on the client-side.
 * */
public class RMIServerService extends ServerInterface implements Serializable {
    @Override
    public void login(String clientID, String username) {
        ServerController.getInstance().submitCPUTask(() ->
            ServerController.getInstance().login(clientID, username)
        );
    }

    @Override
    public void registerClient(ClientInterface client) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Registering new client");
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().registerClient(client)
        );
    }

    @Override
    public void getLobbyInfo(String clientID, int lobbyID) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().getLobbyInfo(clientID, lobbyID)
        );
    }

    @Override
    public void createLobby(String clientID, int playerNum, Totem totem) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().createLobby(clientID, playerNum, totem)
        );
    }

    @Override
    public void joinLobby(String clientID, int lobbyID, Totem totem) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().joinLobby(clientID, lobbyID, totem)
        );
    }

    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().leaveLobby(clientID, lobbyID)
        );
    }

    @Override
    public void startLobby(String clientID, int lobbyID) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().startLobby(clientID, lobbyID)
        );
    }

    @Override
    public void requestAction(String clientID, int lobbyID, PlayerAction action) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().requestAction(clientID, lobbyID, action)
        );
    }

    @Override
    public void getLeaderboard(String clientID, int playerNum) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().getLeaderboard(clientID, playerNum)
        );
    }

    @Override
    public void ping(String clientID) {
        ServerController.getInstance().ping(clientID);
    }

    @Override
    public void disconnect(String clientID) {
        ServerController.getInstance().submitCPUTask(
            () -> ServerController.getInstance().disconnect(clientID)
        );
    }
}
