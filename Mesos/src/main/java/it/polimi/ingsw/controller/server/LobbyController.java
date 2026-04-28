package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;
import javax.swing.*;
import java.util.List;
import java.util.*;

public class LobbyController {
    private int lobbyID;
    private int size;
    private Game model;
    private Map<VirtualClient, Player> players;


    public LobbyController(int lobbyID, int size){
        this.lobbyID = lobbyID;
        this.size = size;
        players = new HashMap<>();
    }


    public void addPlayer(VirtualClient client, Player player) {
        players.put(client, player);
    }


    public void createLobby(int clientID, Player player){
        Lobby lobby = new Lobby(lobbyID, size);
        
        for(VirtualClient client: players.keySet()) {
            client.createLobby(clientID, lobby, player);
        }
    }

    public void joinLobby(int clientID, Player player){
        for(VirtualClient client: players.keySet()){
            client.setLobby(clientID, lobbyID, player);
        }
    }

    public void removePlayer(VirtualClient removedClient) {
        players.remove(removedClient);

        for(VirtualClient client: players.keySet()){
            client.removeFromLobby(removedClient.getID(), lobbyID);
        }
    }


    public void pickCards(VirtualClient pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks){
        model.pick(players.get(pickerClient), topPicks, bottomPicks);

        for(VirtualClient client: players.keySet()){
            client.confirmPick(pickerClient.getID(), model.getBoard().getTopRow(), model.getBoard().getBottomRow(), players.get(pickerClient).getTribe());
        }
    }

    public void setModel(Game game){
        this.model = game;
    }

    public Game getModel() {
        return model;
    }

    public void startLobby(){
        Map<Integer, Tribe> tribes = new HashMap<>();

        model = new Game(PlayerConfig.getPlayerConfig(size), new ArrayList<>(players.values()));

        for(VirtualClient client: players.keySet()){
            tribes.put(client.getID(), players.get(client).getTribe());
        }

        for(VirtualClient client: players.keySet()){
            client.startLobby(client.getID(), lobbyID, model.getBoard(), tribes);
        }
    }

    public int getID(){
        return lobbyID;
    }

    public int getSize(){
        return size;
    }

    public void showRank(int clientID){
        Map<Integer, Integer> rank = new HashMap<>();

        for(VirtualClient client: players.keySet()) {
            rank.put(client.getID(), players.get(client).getRank());
        }

        for(VirtualClient client: players.keySet()){
            if (client.getID() == clientID)
                client.showRank(clientID, lobbyID, rank);
        }
    }

    public Map<VirtualClient, Player> getPlayers() {
        return players;
    }
}
