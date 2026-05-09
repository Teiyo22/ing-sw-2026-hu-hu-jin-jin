package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Set;

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
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removeClient(client.getID(), lobbyController.getID(), removedPlayer);

            client.stopLobby(client.getID(), lobbyController.getID());
            
            for (ClientInterface player: lobbyController.getPlayers().keySet())
                player.stopLobby(client.getID(), lobbyController.getID());

            model.setLobbyState(null);
            lobbyController.setState(new LobbyPausedState(lobbyController));
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        client.showError(client.getID(), "The lobby is already running");
    }

    @Override
    public void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        Player pickerPlayer = lobbyController.getPlayers().get(pickerClient);

        if (validateCardPick(pickerPlayer, topPicks, bottomPicks)) {
            model.pick(pickerPlayer, topPicks, bottomPicks);

            for (ClientInterface client : lobbyController.getPlayers().keySet())
                ; // TODO: notify model changes
        } else {
            pickerClient.showError(pickerClient.getID(), "Invalid action");
        }

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(pickerClient.getID(), lobbyController.getID(), model.getGameState().getModelStateInfo());
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

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(pickerClient.getID(), lobbyController.getID(), model.getGameState().getModelStateInfo());
    }

    @Override
    public void getRank(ClientInterface client) {
        client.showError(client.getID(), "Game not ended yet.");
    }

    private boolean validateCardPick(Player player, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        return true;
    }

    private boolean validateOfferPick(Player player, int offerIndex) {
        return true;
    }

    @Override
    public boolean isShowable() {
        return false;
    }

    @Override
    public boolean isRemovable() {
        return false;
    }
}
