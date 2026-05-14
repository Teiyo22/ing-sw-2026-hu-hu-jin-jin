package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.*;
import java.util.stream.Collectors;

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

        lobbyController.getListeners().clear();
        ServerController.getInstance().addToPlayingClients(lobbyController.getPlayers().keySet());
        ServerController.getInstance().broadcastLobbyRemoval(lobbyController.getID());

        lobbyController.initModel();
        Game model = lobbyController.getModel();

        for (ClientInterface player : lobbyController.getPlayers().keySet()) {
            player.startLobby(lobbyController.getID(), model.getBoard(), new ArrayList<>(lobbyController.getPlayers().values()));
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());
        }

        LobbyRunningState runningState = new LobbyRunningState(lobbyController);
        model.setLobbyState(runningState);
        lobbyController.setState(runningState);

        Logger.getInstance().print(LoggerLevel.SERVER, "Started lobby " + lobbyController.getID());
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removePlayer(lobbyController.getID(), removedPlayer);

            lobbyController.setState(new LobbyWaitingState(lobbyController));
            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);
        client.setCurrLobbyController(lobbyController);

        Set<Player> disconnectedPlayers = new HashSet<>();
        Set<Player> connectedPlayers = lobbyController.getPlayers().values()
                .stream()
                .map(p -> new Player(p.getName(), p.getTotem()))
                .collect(Collectors.toSet());

        client.showLobbyInfo(lobbyController.getID(), connectedPlayers, disconnectedPlayers);
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError("Game not started yet.");
    }

    @Override
    public boolean isShowable() {
        return true;
    }

}
