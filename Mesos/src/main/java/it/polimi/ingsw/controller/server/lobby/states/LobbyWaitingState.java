package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

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
            Logger.getInstance().print(LoggerLevel.DEBUG, "Sent data was validated, adding player to lobby");
            lobbyController.getPlayers().put(client, player);

            Logger.getInstance().print(LoggerLevel.DEBUG, "Notifying listeners of new player");
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

        Map<Integer, Player> playerInfo = new HashMap<>();

        Logger.getInstance().print(LoggerLevel.DEBUG, "Preparing Info of lobby " + lobbyController.getID() + " for client " + client.getID());
        for (Map.Entry<ClientInterface, Player> player: lobbyController.getPlayers().entrySet())
            playerInfo.put(player.getKey().getID(), player.getValue());

        Logger.getInstance().print(LoggerLevel.DEBUG, "Sending lobby info to client " + client.getID());
        client.showLobbyInfo(client.getID(), lobbyController.getID(), playerInfo);
    }

    @Override
    public void pickCards(ClientInterface pickerClient, List<Integer> topPicks, List<Integer> bottomPicks) {
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
