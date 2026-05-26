package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LobbyRunningState extends LobbyState {
    final private Game model;

    public LobbyRunningState(LobbyController lobbyController) {
        super(lobbyController);
        model = lobbyController.getModel();
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError("The lobby is already running");
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError("The lobby is already running");
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        if (lobbyController.getPlayers().containsKey(client)) {
            lobbyController.getListeners().addAll(lobbyController.getPlayers().keySet());

            for (ClientInterface player : lobbyController.getPlayers().keySet())
                player.stopLobby(lobbyController.getID());
            ServerController.getInstance().removeFromPlayingClients(lobbyController.getPlayers().keySet());

            Player removedPlayer = lobbyController.getPlayers().remove(client);
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removeClient(lobbyController.getID(), new Player(removedPlayer.getName(), removedPlayer.getTotem()));

            lobbyController.setState(new LobbyPausedState(lobbyController));
            lobbyController.shutdownGameLoop();
            model.setLobbyState(null);

            ServerController.getInstance().broadcastLobbyAddition(lobbyController.getLobby());
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        client.showError("The lobby is already running");
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        String error = action.canExecute(model);

        if (error.isEmpty())
            action.execute(model);
        else
            client.showError(error);

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());
    }

    public void notifyOfferPick(Player player, int offerIndex) {
        Player playerCopy = player.lightCopy();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), playerCopy, offerIndex);
    }

    public void notifyOfferResolution(Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), player, topRowPicks, bottomRowPicks);
    }

    public void notifyRoundEndUpdate() {
        List<Player> players = lobbyController.getPlayers().values().stream()
            .map(Player::lightCopy)
            .toList();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), players, model.getBoard().getTopRow());
    }

    public void notifyGameEndUpdate() {
        List<Player> players = lobbyController.getPlayers().values().stream()
            .map(Player::lightCopy)
            .toList();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), players);

        lobbyController.setState(new LobbyEndedState(lobbyController));
    }

    @Override
    public boolean isShowable() {
        return false;
    }

}
