package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LobbyFullState extends LobbyState {
    public LobbyFullState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError("The lobby is full");
    }

    @Override
    public void startLobby(ClientInterface client) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Starting lobby " + lobbyController.getID());
        lobbyController.initModel();
        Logger.getInstance().print(LoggerLevel.DEBUG, "Model initialized for lobby " + lobbyController.getID());
        Game model = lobbyController.getModel();

        Map<String, Tribe> tribes = new HashMap<>();
        for (Map.Entry<ClientInterface, Player> player : lobbyController.getPlayers().entrySet())
            tribes.put(player.getKey().getID(), player.getValue().getTribe());

        Logger.getInstance().print(LoggerLevel.DEBUG, "Prepared tribe data for lobby " + lobbyController.getID());

        for (ClientInterface player : lobbyController.getPlayers().keySet()) {
            Logger.getInstance().print(LoggerLevel.DEBUG, "Notifying client " + player.getID() + " of lobby start");
            player.startLobby(lobbyController.getID(), model.getBoard(), tribes);
        }

        for (ClientInterface player : lobbyController.getPlayers().keySet())
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());

        LobbyRunningState nextState = new LobbyRunningState(lobbyController);
        model.setLobbyState(nextState);
        lobbyController.setState(nextState);
        Logger.getInstance().print(LoggerLevel.SERVER, "Started lobby " + lobbyController.getID());
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removePlayer(lobbyController.getID(), removedPlayer);

            lobbyController.setState(new LobbyWaitingState(lobbyController));
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);

        Set<Player> connectedPlayers = new HashSet<>(lobbyController.getPlayers().values());
        Set<Player> disconnectedPlayers = new HashSet<>();

        client.showLobbyInfo(lobbyController.getID(), connectedPlayers, disconnectedPlayers);
    }

    @Override
    public void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        pickerClient.showError("Game not started yet.");
    }

    @Override
    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        pickerClient.showError("Game not started yet.");
    }

    @Override
    public boolean isShowable() {
        return true;
    }

    @Override
    public boolean isRemovable() {
        return false;
    }
}
