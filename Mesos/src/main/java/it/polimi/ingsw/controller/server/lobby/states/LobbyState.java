package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;

import java.util.Set;

public abstract class LobbyState {
    protected final LobbyController lobbyController;

    public LobbyState(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public abstract void joinLobby(ClientInterface client, Player player);
    public abstract void startLobby(ClientInterface client);
    public abstract boolean removeClient(ClientInterface client);
    public abstract void getLobbyInfo(ClientInterface client);
    public abstract void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks);

    public abstract void pickOffer(ClientInterface pickerClient, int offerIndex);
    public abstract void getRank(ClientInterface client);

    public abstract boolean isRemovable();
    public abstract boolean isShowable();
}
