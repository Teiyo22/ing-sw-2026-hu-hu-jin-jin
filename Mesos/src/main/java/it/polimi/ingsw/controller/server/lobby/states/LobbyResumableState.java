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

public class LobbyResumableState extends LobbyState {
    public LobbyResumableState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError("The lobby is full");
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removeClient(lobbyController.getID(), removedPlayer);

            lobbyController.setState(new LobbyPausedState(lobbyController));
            return true;
        }

        return false;
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
