package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.*;

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
                listener.addClient(client.getID(), lobbyController.getID(), player);

            if (missingPlayers.isEmpty())
                lobbyController.setState(new LobbyResumableState(lobbyController));
        } else {
            client.showError(client.getID(), "Player name or totem already used");
        }
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(client.getID(), "Not enough players to start the game");
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            Logger.getInstance().print(LoggerLevel.SERVER, "Removed player " + removedPlayer.getName() + " from lobby");
            missingPlayers.add(removedPlayer);

            for (ClientInterface listener : lobbyController.getListeners()) {
                listener.removeClient(client.getID(), lobbyController.getID(), removedPlayer);
            }

            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);

        Map<Integer, Player> playerInfo = new HashMap<>();

        for (Map.Entry<ClientInterface, Player> player: lobbyController.getPlayers().entrySet())
            playerInfo.put(player.getKey().getID(), player.getValue());

        int key = -1;
        for (Player missingPlayer : missingPlayers) {
            playerInfo.put(key, missingPlayer);
            key--;
        }

        client.showLobbyInfo(client.getID(), lobbyController.getID(), playerInfo);
    }

    @Override
    public void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        pickerClient.showError(pickerClient.getID(), "Game not started yet.");

    }

    @Override
    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        pickerClient.showError(pickerClient.getID(), "Game not started yet.");

    }

    @Override
    public void getRank(ClientInterface client) {
        client.showError(client.getID(), "Game not ended yet.");
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
