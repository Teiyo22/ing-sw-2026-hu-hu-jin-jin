package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LobbyWaitingState extends LobbyState {
    public LobbyWaitingState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        if (validatePlayerInfo(player) && !lobbyController.getPlayers().containsKey(client)) {
            lobbyController.getPlayers().put(client, player);

            for (ClientInterface listener : lobbyController.getListeners())
                listener.addPlayer(lobbyController.getID(), player);

            if (lobbyController.getSize() == lobbyController.getPlayers().size())
                lobbyController.setState(new LobbyFullState(lobbyController));

            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
        } else {
            client.showError("Player name or totem already used");
        }
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError("Not enough players to start the game");
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removePlayer(lobbyController.getID(), removedPlayer);

            if (lobbyController.getPlayers().isEmpty()) {
                ServerController.getInstance().removeLobby(lobbyController.getID());
                ServerController.getInstance().broadcastLobbyRemoval(lobbyController.getID());
            } else {
                ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            }

            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);

        Set<Player> disconnectedPlayers = new HashSet<>();
        Set<Player> connectedPlayers = lobbyController.getPlayers().values()
                .stream()
                .map(p -> new Player(p.getName(), p.getTotem()))
                .collect(Collectors.toSet());

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

    private boolean validatePlayerInfo(Player newPlayer) {
        for (Player players : lobbyController.getPlayers().values())
            if (newPlayer.getTotem() == players.getTotem() || newPlayer.getName().equals(players.getName()))
                return false;
        return true;
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
