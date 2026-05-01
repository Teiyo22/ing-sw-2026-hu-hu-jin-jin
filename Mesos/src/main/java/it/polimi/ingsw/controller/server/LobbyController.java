package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
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
    private Map<ClientInterface, Player> players;

    private Game model = null;


    public LobbyController(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
        players = new ConcurrentHashMap<>();
    }

    public void joinLobby(ClientInterface newClient, Player player) {
        if (players.size() < size) {
            players.putIfAbsent(newClient, player);

            for (ClientInterface client : players.keySet())
                client.setLobby(newClient.getID(), lobbyID, player);

        } else
            getLobbyInfo(newClient);
    }

    public synchronized void getLobbyInfo(ClientInterface client) {
        Map<Integer, Player> lobbyPlayers = new HashMap<>();

        for(Map.Entry<ClientInterface, Player> entry: players.entrySet())
            lobbyPlayers.put(entry.getKey().getID(), entry.getValue());

        client.showLobbyInfo(client.getID(), lobbyID, lobbyPlayers);
    }

    public synchronized void removePlayer(ClientInterface removedClient) {
        if (!players.containsKey(removedClient))
            return;

        players.remove(removedClient);

        if (model == null) {
            for (ClientInterface client : players.keySet())
                client.removeFromLobby(removedClient.getID(), lobbyID);

            if (players.isEmpty())
                ServerController.getInstance().removeWaitingLobby(lobbyID);
        }

        else {
            for (ClientInterface client : players.keySet())
                client.stopLobby(lobbyID);

            ServerController.getInstance().removeRunningLobby(lobbyID);
        }
    }

    public synchronized void pickCards(ClientInterface pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        Player player = players.get(pickerClient);

        if (!validateCardPick(player, topPicks, bottomPicks))
            return;

        model.pick(players.get(pickerClient), topPicks, bottomPicks);

        for (ClientInterface client : players.keySet())
            client.confirmPick(pickerClient.getID(), model.getBoard(), players.get(pickerClient).getTribe());
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
            client.confirmPick(pickerClient.getID(), model.getBoard(), player.getTribe());
    }

    private boolean validateOfferPick(Player player, int offerIndex) {
        return player == model.getGameState().getCurrPlayer() &&
               offerIndex >= 0 && offerIndex < model.getBoard().getOfferTrack().length &&
               model.getBoard().getOfferTrack()[offerIndex].getAssignedPlayer() == null;
    }

    public synchronized boolean startLobby() {
        if(size != players.size())
            return false;

        Map<Integer, Tribe> tribes = new HashMap<>();
        model = new Game(PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));

        for (ClientInterface client : players.keySet())
            tribes.put(client.getID(), players.get(client).getTribe());

        for (ClientInterface client : players.keySet())
            client.startLobby(client.getID(), lobbyID, model.getBoard(), tribes);

        return true;
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
}
