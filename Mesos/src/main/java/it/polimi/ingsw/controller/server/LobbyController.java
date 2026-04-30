package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
import java.util.Map;
import javax.swing.*;
import java.util.List;
import java.util.*;

public class LobbyController {
    private int lobbyID;
    private int size;
    private Map<VirtualClient, Player> players;

    private Game model = null;


    public LobbyController(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
        players = new HashMap<>();
    }

    public synchronized void joinLobby(VirtualClient newClient, Player player) {
        int clientID;

        try {
             clientID = newClient.getID();
        } catch (IOException e) {
            ServerController.getInstance().onClientDisconnected(newClient);
            System.err.println("Failed to contact client, Client disconnected");
            return;
        }

        if (players.size() < size) {
            players.put(newClient, player);

            for (VirtualClient client : players.keySet())
                try {
                    client.setLobby(clientID, lobbyID, player);
                } catch (IOException e) {
                    ServerController.getInstance().onClientDisconnected(client);
                }

        } else {
            getLobbyInfo(newClient);
        }
    }

    public synchronized void getLobbyInfo(VirtualClient client) {
        Map<Integer, Player> lobbyPlayers = new HashMap<>();

        for(Map.Entry<VirtualClient, Player> entry: players.entrySet()) {
            try {
                lobbyPlayers.put(entry.getKey().getID(), entry.getValue());
            } catch (IOException e) {
                ServerController.getInstance().onClientDisconnected(entry.getKey());
            }
        }

        try {
            client.showLobbyInfo(client.getID(), lobbyID, lobbyPlayers);
        } catch (IOException e) {
            ServerController.getInstance().onClientDisconnected(client);
        }
    }

    public synchronized void removePlayer(VirtualClient removedClient) {
        if (!players.containsKey(removedClient))
            return;

        players.remove(removedClient);

        if (model == null) {
            for (VirtualClient client : players.keySet()) {
                client.removeFromLobby(removedClient.getID(), lobbyID);
            }

            if (players.isEmpty())
                ServerController.getInstance().removeWaitingLobby(lobbyID);
        }

        else {
            for (VirtualClient client : players.keySet()) {
                client.terminateLobby(lobbyID);
                ServerController.getInstance().removeRunningLobby(lobbyID);
            }
        }
    }

    public void pickCards(VirtualClient pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        model.pick(players.get(pickerClient), topPicks, bottomPicks);

        for (VirtualClient client : players.keySet()) {
            client.confirmPick(pickerClient.getID(), model.getBoard(), players.get(pickerClient).getTribe());
        }
    }

    public synchronized boolean startLobby() {
        if(size != players.size()) {
            abortStart();
            return false;
        }

        Map<Integer, Tribe> tribes = new HashMap<>();
        model = new Game(PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));

        for (VirtualClient client : players.keySet()) {
            try {
                tribes.put(client.getID(), players.get(client).getTribe());
            } catch (IOException e) {
                ServerController.getInstance().onClientDisconnected(client);
                abortStart();
                return false;
            }
        }

        for (VirtualClient client : players.keySet()) {
            try {
                client.startLobby(client.getID(), lobbyID, model.getBoard(), tribes);
            } catch (IOException e) {
                ServerController.getInstance().onClientDisconnected(client);
                abortStart();
                return false;
            }
        }

        return true;
    }

    private void abortStart() {
        model = null;
        for (Player player : players.values())
            player.setTribe(null);

        for (VirtualClient client : players.keySet()) {
            try {
                client.abortStart(lobbyID);
            } catch (IOException e) {
                ServerController.getInstance().onClientDisconnected(client);
            }
        }
    }

    public void showRank(int clientID) {
        Map<Integer, Integer> rank = new HashMap<>();

        for (VirtualClient client : players.keySet()) {
            rank.put(client.getID(), players.get(client).getRank());
        }

        for (VirtualClient client : players.keySet()) {
            if (client.getID() == clientID)
                client.showRank(clientID, lobbyID, rank);
        }
    }

    public Map<VirtualClient, Player> getPlayers() {
        return players;
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

    public void addPlayer(VirtualClient client, Player player) {
        players.put(client, player);
    }
}
