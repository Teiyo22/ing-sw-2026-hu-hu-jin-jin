package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyFullState extends LobbyState {
    public LobbyFullState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(client.getID(), "The lobby is full");
    }

    @Override
    public void startLobby(ClientInterface client) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Starting lobby " + lobbyController.getID());
        lobbyController.initModel();
        Logger.getInstance().print(LoggerLevel.DEBUG, "Model initialized for lobby " + lobbyController.getID());
        Game model = lobbyController.getModel();

        Map<Integer, Tribe> tribes = new HashMap<>();
        for (Map.Entry<ClientInterface, Player> player : lobbyController.getPlayers().entrySet())
            tribes.put(player.getKey().getID(), player.getValue().getTribe());

        Logger.getInstance().print(LoggerLevel.DEBUG, "Prepared tribe data for lobby " + lobbyController.getID());

        for (ClientInterface player : lobbyController.getPlayers().keySet()) {
            Logger.getInstance().print(LoggerLevel.DEBUG, "Notifying client " + player.getID() + " of lobby start");
            player.startLobby(player.getID(), lobbyController.getID(), model.getBoard(), tribes);
        }

        lobbyController.setState(new LobbyRunningState(lobbyController));
        Logger.getInstance().print(LoggerLevel.SERVER, "Started lobby " + lobbyController.getID());
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removePlayer(client.getID(), lobbyController.getID(), removedPlayer);

            lobbyController.setState(new LobbyWaitingState(lobbyController));
            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        lobbyController.getListeners().add(client);

        Map<Integer, Player> playerInfo = new HashMap<>();

        for (Map.Entry<ClientInterface, Player> player : lobbyController.getPlayers().entrySet())
            playerInfo.put(player.getKey().getID(), player.getValue());

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

    @Override
    public boolean isShowable() {
        return true;
    }

    @Override
    public boolean isRemovable() {
        return false;
    }
}
