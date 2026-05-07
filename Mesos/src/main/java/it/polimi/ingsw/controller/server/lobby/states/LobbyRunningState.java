package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class LobbyRunningState extends LobbyState {
    final private Game model;

    public LobbyRunningState(LobbyController lobbyController) {
        super(lobbyController);
        model = lobbyController.getModel();
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(client.getID(), "The lobby is already running");
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(client.getID(), "The lobby is already running");
    }

    @Override
    public boolean removeFromLobby(ClientInterface client) {
        return true;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        client.showError(client.getID(), "The lobby is already running");
    }

    @Override
    public Lobby getLobby() {
        return null;
    }

    @Override
    public void pickCards(ClientInterface pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        Player pickerPlayer = lobbyController.getPlayers().get(pickerClient);

        if (validateCardPick(pickerPlayer, topPicks, bottomPicks)) {
            model.pick(pickerPlayer, topPicks, bottomPicks);

            for (ClientInterface client : lobbyController.getPlayers().keySet())
                ; // TODO: notify model changes
        } else {
            pickerClient.showError(pickerClient.getID(), "Invalid action");
        }
    }

    @Override
    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        Player pickerPlayer = lobbyController.getPlayers().get(pickerClient);

        if (validateOfferPick(pickerPlayer, offerIndex)) {
            model.assignTo(pickerPlayer, model.getBoard().getOfferTrack()[offerIndex]);

            for (ClientInterface client : lobbyController.getPlayers().keySet())
                ; // TODO: notify model changes
        } else {
            pickerClient.showError(pickerClient.getID(), "Invalid action");
        }
    }

    @Override
    public void getRank(ClientInterface client) {
        client.showError(client.getID(), "Game not ended yet.");
    }

    private boolean validateCardPick(Player player, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        return true;
    }

    private boolean validateOfferPick(Player player, int offerIndex) {
        return true;
    }
}
