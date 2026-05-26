package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.*;
import java.util.stream.Collectors;

public class LobbyPausedState extends LobbyState {
    private List<Player> missingPlayers;

    public LobbyPausedState(LobbyController lobbyController) {
        super(lobbyController);
        missingPlayers = new ArrayList<>(lobbyController.getModel().getPlayers().stream()
                .filter(p -> !lobbyController.getPlayers().containsValue(p))
                .toList());
    }

    @Override
    public void joinLobby(ClientInterface client, Player newPlayer) {
        Player player = null;

        for (Player missingPlayer : missingPlayers) {
            if (missingPlayer.equals(newPlayer))
                player = missingPlayer;
        }

        if (player != null && !lobbyController.getPlayers().containsKey(client)) {
            lobbyController.getPlayers().put(client, player);
            missingPlayers.remove(player);

            for (ClientInterface listener : lobbyController.getListeners())
                listener.addPlayer(lobbyController.getID(), player);

            if (missingPlayers.isEmpty())
                lobbyController.setState(new LobbyResumableState(lobbyController));

            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
        } else if (lobbyController.getPlayers().containsKey(client)) {
            client.showError(new ErrorMessage("Join Lobby Error", "Already in the lobby"));
        } else {
            client.showError(new ErrorMessage("Join Lobby Error", "Invalid name or totem"));
        }
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(new ErrorMessage("Lobby Start Error", "Not enough players to start the game"));
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            Logger.getInstance().print(LoggerLevel.SERVER, "Removed player " + removedPlayer.getName() + " from lobby");
            missingPlayers.add(removedPlayer);

            for (ClientInterface listener : lobbyController.getListeners())
                listener.removeClient(lobbyController.getID(), new Player(removedPlayer.getName(), removedPlayer.getTotem()));

            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);
        client.setCurrLobbyController(lobbyController);

        Set<Player> disconnectedPlayers = missingPlayers.stream()
                .map(p -> new Player(p.getName(), p.getTotem()))
                .collect(Collectors.toSet());
        Set<Player> connectedPlayers = lobbyController.getPlayers().values().stream()
                .map(p -> new Player(p.getName(), p.getTotem()))
                .collect(Collectors.toSet());

        client.showLobbyInfo(lobbyController.getID(), connectedPlayers, disconnectedPlayers);
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError(new ErrorMessage("Lobby Action Error", "The lobby is paused"));
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
