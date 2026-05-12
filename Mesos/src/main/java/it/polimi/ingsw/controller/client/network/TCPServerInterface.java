package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.requests.*;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;
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
        serverHandler.sendMessage(request);
    }

    /**
     * Method to create a new lobby of a certain size and join it.
     *
     * @param clientID
     * @param playerNum Game size (number of players in the game).
     * @param player    Player object containing the player's username and selected totem.
     *
     */
    @Override
    public void createLobby(String clientID, int playerNum, Player player) {
        CreateLobbyRequest request = new CreateLobbyRequest(clientID, playerNum, player);
        serverHandler.sendMessage(request);
    }


    /**
     * Method to join the selected lobby.
     *
     * @param clientID
     * @param lobbyID  ID of the lobby of interest.
     * @param player   Player object containing the player's username and selected totem.
     *
     */
    @Override
    public void joinLobby(String clientID, int lobbyID, Player player) {
        JoinLobbyRequest request = new JoinLobbyRequest(clientID, lobbyID, player);
        serverHandler.sendMessage(request);
    }


    /** Method to leave the lobby.
     * */
    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        LeaveLobbyRequest request = new LeaveLobbyRequest(clientID, lobbyID);
        serverHandler.sendMessage(request);
    }


    /** Method to start the lobby.
     * */
    @Override
    public void startLobby(String clientID, int lobbyID) {
        StartLobbyRequest request = new StartLobbyRequest(clientID, lobbyID);
        serverHandler.sendMessage(request);
    }


    /** Method to get the existing lobbies that are still waiting for players.
     * */
    @Override
    public void getWaitingLobbies(String clientID) {
        WaitingLobbyRequest request = new WaitingLobbyRequest(clientID);
        serverHandler.sendMessage(request);
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
        serverHandler.sendMessage(request);
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
        serverHandler.sendMessage(request);
    }


    /**
     * Method to request card picks from the server.
     *
     * @param clientID
     * @param lobbyID     ID of the player's lobby
     * @param topPicks    list of the cards that the player would like to pick from the top row.
     * @param bottomPicks list of the cards that the player would like to pick from the bottom row.
     *
     */
    @Override
    public void requestCards(String clientID, int lobbyID, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        PickCardsRequest request = new PickCardsRequest(clientID, lobbyID, topPicks, bottomPicks);
        serverHandler.sendMessage(request);
    }


    /**
     * Method to request a specific offer from the server.
     *
     * @param clientID
     * @param lobbyID    ID of the player's lobby
     * @param offerIndex index of the offer of interest.
     *
     */
    @Override
    public void requestOffer(String clientID, int lobbyID, int offerIndex) {
        PickOfferRequest request = new PickOfferRequest(clientID, lobbyID, offerIndex);
        serverHandler.sendMessage(request);
    }

    @Override
    public void ping(String clientID) {
        PingRequest request = new PingRequest(clientID);
        serverHandler.sendMessage(request);
    }

    @Override
    public void disconnect() {
        serverHandler.cleanup();
    }
}
