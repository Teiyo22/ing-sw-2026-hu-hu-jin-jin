package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Tribe;

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
    private boolean running = false;


    public LobbyController(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
    }

    public synchronized void joinLobby(ClientInterface newClient, Player newPlayer) {
        // To avoid sending the same info twice and letting a player not "listening" to the lobby join
        if (!listeners.contains(newClient))
            return;

        if (running)
            // TODO : send error message to newClient
            return;

        if (players.size() == size)
            // TODO : send error message to newClient
            return;

        if (!players.containsKey(newClient))
            // TODO : send error message to newClient
            return;

        if (!validatePlayerInfo(newPlayer))
            // TODO : send error message to newClient
            return;

        listeners.remove(newClient);
        players.put(newClient, newPlayer);

        for (ClientInterface lobbyClient : players.keySet())
            lobbyClient.addToLobby(newClient.getID(), lobbyID, newPlayer);

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

        if (running)
            // TODO: send error message to client
            return;

        listeners.add(client);

        Map<Integer, Player> lobbyPlayers = new HashMap<>();

        for (ClientInterface lobbyClient : players.keySet())
            lobbyPlayers.put(lobbyClient.getID(), players.get(lobbyClient));

        client.showLobbyInfo(client.getID(), lobbyID, lobbyPlayers);
    }

    public synchronized boolean removeFromLobby(ClientInterface removedClient) {
        listeners.remove(removedClient);

        Player removedPlayer = players.remove(removedClient);
        if (removedPlayer == null)
            return false;

        if (!running) {
            for (ClientInterface lobbyClient : players.keySet())
                lobbyClient.removeFromLobby(removedClient.getID(), lobbyID);

            for (ClientInterface client : players.keySet())
                client.removeFromLobby(removedClient.getID(), lobbyID);
        } else {
            for (ClientInterface client : players.keySet())
                client.stopLobby(lobbyID);
        }

        return true;
    }

    public synchronized boolean startLobby(ClientInterface startClient) {
        if (running)
            // TODO : send error message to startClient
            return false;

        if (size != players.size())
            // TODO : send error message to startClient
            return false;

        model = new Game(PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));

        Map<Integer, Tribe> tribes = new HashMap<>();
        for (ClientInterface client : players.keySet())
            tribes.put(client.getID(), players.get(client).getTribe());

        for (ClientInterface client : players.keySet())
            client.startLobby(client.getID(), lobbyID, model.getBoard(), tribes);

        running = true;
        return true;
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
}
