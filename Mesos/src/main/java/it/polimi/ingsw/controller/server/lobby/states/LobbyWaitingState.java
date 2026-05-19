package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.action.PlayerAction;
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
            client.showError("Totem already used");
        }
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError("Not enough players to start the game");
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Map<ClientInterface, Player> playersMap = lobbyController.getPlayers();

        ClientInterface targetKey = null;
        Player removedPlayer = null;

        for (Map.Entry<ClientInterface, Player> entry : playersMap.entrySet()) {
            if (entry.getKey().getID().equals(client.getID())) {
                targetKey = entry.getKey();
                removedPlayer = entry.getValue();
                break;
            }
        }
        if (targetKey != null) {
            playersMap.remove(targetKey);

            for (ClientInterface listener : lobbyController.getListeners()) {
                listener.removePlayer(lobbyController.getID(), removedPlayer);
            }

            if (playersMap.isEmpty()) {
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
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError("Game not started yet.");
    }

    private boolean validatePlayerInfo(Player newPlayer) {
        for (Player player : lobbyController.getPlayers().values())
            if (newPlayer.getTotem() == player.getTotem())
                return false;
        return true;
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
