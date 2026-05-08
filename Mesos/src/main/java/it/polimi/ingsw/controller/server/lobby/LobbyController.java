package it.polimi.ingsw.controller.server.lobby;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.server.lobby.states.LobbyState;
import it.polimi.ingsw.controller.server.lobby.states.LobbyWaitingState;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;

import java.util.Map;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyController {
    private final int lobbyID;
    private final int size;

    private final Map<ClientInterface, Player> players = new ConcurrentHashMap<>();
    private final Set<ClientInterface> listeners = ConcurrentHashMap.newKeySet();

    private Game model = null;
    private LobbyState state;


    public LobbyController(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
        state = new LobbyWaitingState(this);
    }

    //=============================================================================
    // Lobby Interaction methods
    //=============================================================================

    public synchronized void joinLobby(ClientInterface newClient, Player newPlayer) {
        state.joinLobby(newClient, newPlayer);
    }

    public synchronized void getLobbyInfo(ClientInterface client) {
        state.getLobbyInfo(client);
    }

    public synchronized void startLobby(ClientInterface startClient) {
        state.startLobby(startClient);
    }

    public synchronized boolean removeClient(ClientInterface leaveClient) {
        return state.removeClient(leaveClient);
    }

    //=============================================================================
    // Model Interaction methods
    //=============================================================================

    public void initModel() {
        if (model == null)
            model = new Game(this, PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));
    }

    public synchronized void pickCards(ClientInterface pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        state.pickCards(pickerClient, topPicks, bottomPicks);
    }

    public synchronized void pickOffer(ClientInterface pickerClient, int offerIndex) {
        state.pickOffer(pickerClient, offerIndex);
    }

    public synchronized void getRank(ClientInterface requester) {
        state.getRank(requester);
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public int getID() {
        return lobbyID;
    }

    public int getSize() {
        return size;
    }

    public Map<ClientInterface, Player> getPlayers() {
        return players;
    }

    public Set<ClientInterface> getListeners() {
        return listeners;
    }

    public Game getModel() {
        return model;
    }

    public Lobby getLobby() {
        return new Lobby(lobbyID, size);
    }

    public boolean isShowable() {
        return state.isShowable();
    }

    public boolean isRemovable() {
        return state.isRemovable();
    }

    //=============================================================================
    // Setters
    //=============================================================================

    public void setState(LobbyState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "[Lobby " + lobbyID + "]";
    }
}
