package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;
import java.util.Set;
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
            for (ClientInterface player : lobbyController.getPlayers().keySet())
                player.stopLobby(lobbyController.getID());

            Player removedPlayer = lobbyController.getPlayers().remove(client);

            lobbyController.getListeners().addAll(lobbyController.getPlayers().keySet());
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removeClient(lobbyController.getID(), removedPlayer);

            ServerController.getInstance().moveToClients(lobbyController.getPlayers().keySet());

            lobbyController.setState(new LobbyPausedState(lobbyController));
            model.setLobbyState(null);
            ServerController.getInstance().broadcastLobbyAddition(lobbyController.getID());
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        client.showError("The lobby is already running");
    }

    @Override
    public void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        Player pickerPlayer = lobbyController.getPlayers().get(pickerClient);

        if (!model.pick(pickerPlayer, topPicks, bottomPicks))
            pickerClient.showError("Invalid action");

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());
    }

    @Override
    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        Player pickerPlayer = lobbyController.getPlayers().get(pickerClient);

        if (!model.assignTo(pickerPlayer, model.getBoard().getOfferTrack()[offerIndex]))
            pickerClient.showError("Invalid action");

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());
    }

    public void notifyOfferPick() {
        OrderSlot[] orderTile = model.getBoard().getOrderTile();
        OfferTile[] offerTrack = model.getBoard().getOfferTrack();

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateModel(lobbyController.getID(), orderTile, offerTrack);
    }

    public void notifyOfferResolution(Player player) {
        Board board = model.getBoard();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), player, player.getTribe(), board);
    }

    public void notifyExtraActionResolution(Player player) {
        Row topRow = model.getBoard().getTopRow();

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), player, player.getTribe(), topRow);
    }

    public void notifyRoundEndUpdate() {
        Board board = model.getBoard();
        Map<String, Tribe> tribes = lobbyController.getPlayers().entrySet().stream()
                .map(e -> Map.entry(e.getKey().getID(), e.getValue().getTribe()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), tribes, board.getTopRow(), board.getBottomRow());
    }

    public void notifyGameEndUpdate() {
        Map<String, Tribe> tribes = lobbyController.getPlayers().entrySet().stream()
                .map(e -> Map.entry(e.getKey().getID(), e.getValue().getTribe()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, Integer> ranking = lobbyController.getPlayers().entrySet().stream()
                .map(e -> Map.entry(e.getKey().getID(), e.getValue().getRank()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (ClientInterface client : lobbyController.getPlayers().keySet())
            client.updateModel(lobbyController.getID(), tribes, ranking);

        lobbyController.setState(new LobbyEndedState(lobbyController));
    }

    @Override
    public boolean isShowable() {
        return false;
    }

}
