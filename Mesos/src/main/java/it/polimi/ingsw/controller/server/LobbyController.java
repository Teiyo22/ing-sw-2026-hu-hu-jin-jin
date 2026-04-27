package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class LobbyController {
    private int id;
    private int size;
    private Game model;
    private Map<VirtualClient, Player> players;

    public LobbyController(int id) {
        this.id = id;
    }

    public void addPlayer(VirtualClient client, Player player) {
        players.put(client, player);
    }

    public void removePlayer(VirtualClient client) {
    }

    public void setID(int id) {
        this.id = id;
    }

    public Game getModel() {
        return model;
    }

    public int getSize() {
        return size;
    }

    public int getID() {
        return id;
    }

    public Map<VirtualClient, Player> getPlayers() {
        return players;
    }
}