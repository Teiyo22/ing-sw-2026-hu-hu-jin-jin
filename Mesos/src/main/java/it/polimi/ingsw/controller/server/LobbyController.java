package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.Map;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyController {
    private int lobbyID;
    private int size;

    private final Map<ClientInterface, Player> players = new ConcurrentHashMap<>();
    private final List<ClientInterface> listeners = new ArrayList<>();

    private Game model = null;
    private LobbyState state = LobbyState.WAITING;

    public enum LobbyState {
        WAITING ("Waiting for players"),
        STARTABLE ("Lobby full"),
        RUNNING ("Game is running"),
        FINISHED ("Game ended");

        private String errorMsg;

        LobbyState(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    public LobbyController(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
    }

    public synchronized void joinLobby(ClientInterface newClient, Player newPlayer) {
        // To avoid sending the same info twice and letting a player not "listening" to the lobby join
        if (!listeners.contains(newClient))
            return;

        if (state != LobbyState.WAITING) {
            newClient.showError(newClient.getID(), state.errorMsg);
            return;
        }

        if (players.containsKey(newClient)) {
            newClient.showError(newClient.getID(), "You are already in this lobby");
            return;
        }

        if (!validatePlayerInfo(newPlayer)) {
            newClient.showError(newClient.getID(), "Not valid totem or username");
            return;
        }

        players.put(newClient, newPlayer);

        if (players.size() == size)
            state = LobbyState.STARTABLE;

        for (ClientInterface lobbyListener : listeners)
            lobbyListener.addToLobby(newClient.getID(), lobbyID, newPlayer);
    }

    private boolean validatePlayerInfo(Player newPlayer) {
        for (Player lobbyPlayer : players.values())
            if (newPlayer.getTotem() == lobbyPlayer.getTotem() || newPlayer.getName().equals(lobbyPlayer.getName()))
                return false;
        return true;
    }

    public synchronized void getLobbyInfo(ClientInterface client) {
        if (listeners.contains(client))
            return;

        if (state != LobbyState.WAITING && state != LobbyState.STARTABLE) {
            client.showError(client.getID(), state.errorMsg);
            return;
        }

        listeners.add(client);

        Map<Integer, Player> lobbyPlayers = new HashMap<>();

        for (ClientInterface lobbyClient : players.keySet())
            lobbyPlayers.put(lobbyClient.getID(), players.get(lobbyClient));

        client.showLobbyInfo(client.getID(), lobbyID, lobbyPlayers);
    }

    public synchronized boolean removePlayer(ClientInterface removedClient) {
        Player removedPlayer = players.remove(removedClient);

        if (removedPlayer == null)
            return false;

        for (ClientInterface listener : listeners)
            listener.removeFromLobby(removedClient.getID(), lobbyID);

        if (state == LobbyState.STARTABLE)
            state = LobbyState.WAITING;

        return true;
    }

    public synchronized void removeListener(ClientInterface removedClient) {
        listeners.remove(removedClient);
    }

    public synchronized void startLobby(ClientInterface startClient) {
        if (state != LobbyState.STARTABLE) {
            startClient.showError(startClient.getID(), state.errorMsg);
            return;
        }

        model = new Game(this, PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));

        Map<Integer, Tribe> tribes = new HashMap<>();
        for (ClientInterface client : players.keySet())
            tribes.put(client.getID(), players.get(client).getTribe());

        for (ClientInterface listener : listeners)
            listener.startLobby(listener.getID(), lobbyID, model.getBoard(), tribes);

        listeners.clear();

        state = LobbyState.RUNNING;
        Logger.getInstance().print(LoggerLevel.SERVER, "Successfully started lobby " + lobbyID);
    }

    public synchronized void stopLobby() {
        for (ClientInterface clientInterface: players.keySet())
            clientInterface.removeLobby(lobbyID);
    }

    public synchronized void pickCards(ClientInterface pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        Player player = players.get(pickerClient);

        if (!validateCardPick(player, topPicks, bottomPicks))
            return;

        model.pick(players.get(pickerClient), topPicks, bottomPicks);

        for (ClientInterface client : players.keySet())
            client.updateModel(pickerClient.getID(), model.getBoard(), players.get(pickerClient).getTribe());
    }

    private boolean validateCardPick(Player player, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        return player == model.getGameState().getCurrPlayer();
    }

    public synchronized void pickOffer(ClientInterface pickerClient, int offerIndex) {
        Player player = players.get(pickerClient);

        if (!validateOfferPick(player, offerIndex))
            return;

        model.assignTo(player, model.getBoard().getOfferTrack()[offerIndex]);

        for (ClientInterface client : players.keySet())
            client.updateModel(pickerClient.getID(), model.getBoard(), player.getTribe());
    }

    private boolean validateOfferPick(Player player, int offerIndex) {
        return player == model.getGameState().getCurrPlayer() &&
                offerIndex >= 0 && offerIndex < model.getBoard().getOfferTrack().length &&
                model.getBoard().getOfferTrack()[offerIndex].getAssignedPlayer() == null;
    }

    public synchronized void showRank(ClientInterface requester) {
        if (state != LobbyState.FINISHED){
            requester.showError(requester.getID(), state.errorMsg);
            return;
        }

        Map<Integer, Integer> rank = new HashMap<>();

        for (ClientInterface client : players.keySet())
            rank.put(client.getID(), players.get(client).getRank());

        requester.showRank(requester.getID(), lobbyID, rank);
    }


    public Lobby getLobby() {
        return new Lobby(lobbyID, size);
    }

    public int getID() {
        return lobbyID;
    }

    public int getSize() {
        return size;
    }

    public void addPlayer(ClientInterface client, Player player) {
        players.put(client, player);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public LobbyState getState() {
        return state;
    }

    public void setState(LobbyState state) {
        this.state = state;
    }
}
