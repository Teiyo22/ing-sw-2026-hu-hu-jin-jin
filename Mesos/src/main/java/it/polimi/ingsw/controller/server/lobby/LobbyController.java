package it.polimi.ingsw.controller.server.lobby;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.server.lobby.states.LobbyState;
import it.polimi.ingsw.controller.server.lobby.states.LobbyWaitingState;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;

import java.util.Map;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LobbyController {
    private final int lobbyID;
    private final int size;

    private final Map<ClientInterface, Player> players = new ConcurrentHashMap<>();
    private final Set<ClientInterface> listeners = ConcurrentHashMap.newKeySet();

    private Game model = null;
    private LobbyState state;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public LobbyController(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
        state = new LobbyWaitingState(this);
    }

    //=============================================================================
    // Lobby Interaction methods
    //=============================================================================

    public void joinLobby(ClientInterface newClient, Player newPlayer) {
        writeLock.lock();
        try {
            state.joinLobby(newClient, newPlayer);
        } finally {
            writeLock.unlock();
        }
    }

    public void getLobbyInfo(ClientInterface client) {
        readLock.lock();
        try {
            state.getLobbyInfo(client);
        } finally {
            readLock.unlock();
        }
    }

    public void startLobby(ClientInterface startClient) {
        writeLock.lock();
        try {
            state.startLobby(startClient);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean remove(ClientInterface client) {
        writeLock.lock();
        try {
            return state.removeClient(client);
        } finally {
            writeLock.unlock();
        }
    }

    public void add(ClientInterface client, Player player) {
        listeners.add(client);
        players.put(client, player);
    }

    //=============================================================================
    // Model Interaction methods
    //=============================================================================

    public void initModel() {
        if (model == null)
            model = new Game(PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));
    }

    public void pickCards(ClientInterface pickerClient, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        writeLock.lock();
        try {
            state.pickCards(pickerClient, topPicks, bottomPicks);
        } finally {
            writeLock.unlock();
        }
    }

    public void pickOffer(ClientInterface pickerClient, int offerIndex) {
        writeLock.unlock();
        try {
            state.pickOffer(pickerClient, offerIndex);
        } finally {
            writeLock.unlock();
        }
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
        return new Lobby(lobbyID, size, players.size());
    }

    public boolean isShowable() {
        return state.isShowable();
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
