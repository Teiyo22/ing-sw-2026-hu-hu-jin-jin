package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;

import java.util.Set;

public class LobbyEndedState extends LobbyState {
    public LobbyEndedState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(client.getID(), "The lobby already ended.");
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(client.getID(), "The lobby already ended.");

    }

    @Override
    public boolean removeClient(ClientInterface client) {
        return true;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        client.showError(client.getID(), "The lobby already ended.");
    }

    @Override
    public void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        pickerClient.showError(pickerClient.getID(), "The lobby already ended.");
    }

    @Override
    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        pickerClient.showError(pickerClient.getID(), "The lobby already ended.");
    }

    @Override
    public void getRank(ClientInterface client) {

    }

    @Override
    public boolean isShowable() {
        return false;
    }

    @Override
    public boolean isRemovable() {
        return lobbyController.getPlayers().isEmpty();
    }
}
