package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public abstract class LobbyState {
    protected final LobbyController lobbyController;

    public LobbyState(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public abstract void joinLobby(ClientInterface client, Player player);
    public abstract void startLobby(ClientInterface client);
    public abstract boolean removeFromLobby(ClientInterface client);
    public abstract void getLobbyInfo(ClientInterface client);
    public abstract Lobby getLobby();

    public abstract void pickCards(ClientInterface pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks);
    public abstract void pickOffer(ClientInterface pickerClient, int offerIndex);
    public abstract void getRank(ClientInterface client);
}
