package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LobbyState {
    protected final LobbyController lobbyController;

    public LobbyState(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }
    public abstract boolean removeClient(ClientInterface client);
    public abstract void playAction(ClientInterface client, PlayerAction action);
    public abstract boolean isShowable();
    public abstract void joinLobby(ClientInterface client, Player player);

    public void startLobby(ClientInterface client) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Starting lobby " + lobbyController.getID());

        lobbyController.getListeners().clear();
        ServerController.getInstance().addToPlayingClients(lobbyController.getPlayers().keySet());
        ServerController.getInstance().broadcastLobbyRemoval(lobbyController.getID());
        lobbyController.initGameLoop();

        lobbyController.initModel();
        Game model = lobbyController.getModel();

        for (ClientInterface player : lobbyController.getPlayers().keySet()) {
            player.startLobby(lobbyController.getID(), model.getBoard().deepCopy(), new ArrayList<>(lobbyController.getPlayers().values()));
            player.updateState(lobbyController.getID(), model.getGameState().getModelStateInfo());
        }

        LobbyRunningState runningState = new LobbyRunningState(lobbyController);
        model.setLobbyState(runningState);
        lobbyController.setState(runningState);

        Logger.getInstance().print(LoggerLevel.SERVER, "Started lobby " + lobbyController.getID());
    }

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
}
