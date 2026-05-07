package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyWaitingState extends LobbyState {
    public LobbyWaitingState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        if (validatePlayerInfo(player) && !lobbyController.getPlayers().containsKey(client)) {
            lobbyController.getPlayers().put(client, player);

            for (ClientInterface listener : lobbyController.getListeners())
                listener.addPlayer(client.getID(), lobbyController.getID(), player);

            if (lobbyController.getSize() == lobbyController.getPlayers().size())
                lobbyController.setState(new LobbyFullState(lobbyController));
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
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removePlayer(client.getID(), lobbyController.getID(), removedPlayer);

            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);

        Map<Player, Integer> playerInfo = new HashMap<>();

        for (Map.Entry<ClientInterface, Player> player: lobbyController.getPlayers().entrySet())
            playerInfo.put(player.getValue(), player.getKey().getID());

        client.showLobbyInfo(client.getID(), lobbyController.getID(), playerInfo);
    }

    @Override
    public void pickCards(ClientInterface pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        pickerClient.showError(pickerClient.getID(), "Game not started yet.");
    }

    @Override
    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        pickerClient.showError(pickerClient.getID(), "Game not started yet.");
    }

    @Override
    public void getRank(ClientInterface client) {
        client.showError(client.getID(), "Game not started yet.");
    }

    private boolean validatePlayerInfo(Player newPlayer) {
        for (Player players : lobbyController.getPlayers().values())
            if (newPlayer.getTotem() == players.getTotem() || newPlayer.getName().equals(players.getName()))
                return false;
        return true;
    }

    @Override
    public boolean isRemovable() {
        return lobbyController.getPlayers().isEmpty();
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
