package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.requests.*;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Totem;

import java.util.Set;

public class TCPServerInterface extends ServerInterface {
    private final NetworkClient serverHandler;

    public TCPServerInterface(ClientController clientController, NetworkClient serverHandler) {
        super(clientController);
        this.serverHandler = serverHandler;
        serverHandler.setServer(this);
    }

    /** Method to interpret and handle messages.
     * Calls the message's receive method which will call ClientController's methods according to the message.
     * @param response Message received from the NetworkClient.
     * */
    public void handleMessage(Response response) {
        response.receive(clientController);
    }

    public void login(String clientID, String username) {
        LoginRequest request = new LoginRequest(clientID, username);
        sendMessage(request);
    }

    /**
     * Method to create a new lobby of a certain size and join it.
     *
     * @param clientID
     * @param playerNum Game size (number of players in the game).
     * @param totem     Player object containing the player's username and selected totem.
     *
     */
    @Override
    public void createLobby(String clientID, int playerNum, Totem totem) {
        CreateLobbyRequest request = new CreateLobbyRequest(clientID, playerNum, totem);
        sendMessage(request);
    }


    /**
     * Method to join the selected lobby.
     *
     * @param clientID
     * @param lobbyID  ID of the lobby of interest.
     * @param totem    Player object containing the player's username and selected totem.
     *
     */
    @Override
    public void joinLobby(String clientID, int lobbyID, Totem totem) {
        JoinLobbyRequest request = new JoinLobbyRequest(clientID, lobbyID, totem);
        sendMessage(request);
    }


    /** Method to leave the lobby.
     * */
    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        LeaveLobbyRequest request = new LeaveLobbyRequest(clientID, lobbyID);
        sendMessage(request);
    }


    /** Method to start the lobby.
     * */
    @Override
    public void startLobby(String clientID, int lobbyID) {
        StartLobbyRequest request = new StartLobbyRequest(clientID, lobbyID);
        sendMessage(request);
    }

    /**
     * Method to get more information regarding a certain selected lobby.
     *
     * @param clientID
     * @param lobbyID  ID of the lobby of interest.
     *
     */
    @Override
    public void getLobbyInfo(String clientID, int lobbyID) {
        LobbyInfoRequest request = new LobbyInfoRequest(clientID, lobbyID);
        sendMessage(request);
    }


    /**
     * Method to get the leaderboard of top players in games of a certain size.
     *
     * @param clientID
     * @param playerNum Game size for which the player requests the leaderboard.
     *
     */
    @Override
    public void getLeaderboard(String clientID, int playerNum) {
        GetLeaderboardRequest request = new GetLeaderboardRequest(clientID, playerNum);
        sendMessage(request);
    }

    /**
     * Method to play a specific action.
     *
     * @param clientID
     * @param lobbyID    ID of the player's lobby
     * @param action action that the player wants to play.
     *
     */
    @Override
    public void requestAction(String clientID, int lobbyID, PlayerAction action) {
        PlayerActionRequest request = new PlayerActionRequest(clientID, lobbyID, action);
        sendMessage(request);
    }

    @Override
    public void ping(String clientID) {
        PingRequest request = new PingRequest(clientID);
        sendMessage(request);
    }

    @Override
    public void disconnect() {
        isConnected = false;
        serverHandler.cleanup();
    }

    private void sendMessage(Request message) {
        if (isConnected)
            serverHandler.sendMessage(message);
    }
}
