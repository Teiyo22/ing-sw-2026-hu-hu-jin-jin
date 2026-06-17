package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.controller.client.EventResult;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class LobbyRunningState extends LobbyState {
    final private Game model;

    public LobbyRunningState(LobbyController lobbyController) {
        super(lobbyController);
        model = lobbyController.getModel();
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(new ErrorMessage("Join Lobby Error", "Game is running"));
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(new ErrorMessage("Start Lobby Error", "Game is running"));
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
        client.showError(new ErrorMessage("Lobby Info Error", "The lobby is already running"));
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        System.out.println("WTF2");
        String[] errors = action.canExecute(model);
        System.out.println("WTF");
        if (errors.length == 0)
            action.execute(model);
        else
            client.showError(new ErrorMessage("Lobby Action Error", errors));

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());
    }

    public void notifyOfferPick(Player player, int offerIndex) {
        Player playerCopy = player.shallowCopy();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), playerCopy, offerIndex);
    }

    public void notifyOfferResolution(Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), player.mediumCopy(), topRowPicks, bottomRowPicks);
    }

    public void notifyRoundEndUpdate(boolean eraChanged) {
        List<Player> players = lobbyController.getPlayers().values().stream()
            .map(Player::mediumCopy)
            .toList();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), players, model.getBoard().getTopRow(), eraChanged);
    }

    public void notifyGameEndUpdate() {
        List<Player> players = lobbyController.getPlayers().values().stream()
            .map(Player::mediumCopy)
            .toList();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), players);

        ServerController.getInstance().updateLeaderboard(model);
        lobbyController.setState(new LobbyEndedState(lobbyController));
    }

    public void notifyEventResolution(String context, List<EventResult> results) {
        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.showEventResults(new EventResultMessage(lobbyController.getID(), context, results));
    }

    @Override
    public boolean isShowable() {
        return false;
    }
}
