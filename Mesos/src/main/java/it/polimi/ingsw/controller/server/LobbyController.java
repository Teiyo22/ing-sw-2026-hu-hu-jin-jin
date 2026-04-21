package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Totem;

import java.util.List;

public class LobbyController {
    private int id;
    private Game model;
    private List<VirtualClient> clients;

    public void addPlayer(VirtualClient client, String playerName, Totem totem) {
    }

    public void removePlayer(VirtualClient client) {
    }

    public void setID(int id) {
        this.id = id;
    }

    public Game getModel() {
        return model;
    }
}