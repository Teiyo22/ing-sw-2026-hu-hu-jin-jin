package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;

import javax.swing.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

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
        size++;

        players.put(client, player);
    }


    public void createLobby(int clientID, Player player){

        for(VirtualClient client: players.keySet()){
            client.createLobby(clientID, size, player);
        }
    }

    public void joinLobby(int clientID, Player player){

        for(VirtualClient client: players.keySet()){
            client.setLobby(clientID, lobbyID, player);
        }
    }

    public void removePlayer(VirtualClient client) {
        size--;

        players.remove(client);
    }


    public void pickCards(VirtualClient pickerClient, List<Pickable> topPicks, List<Pickable> bottomPicks){

        model.pick(players.get(pickerClient), topPicks, bottomPicks);

        for(VirtualClient client: players.keySet()){
            client.confirmPick(pickerClient.getID(), model.getBoard().getTopRow(), model.getBoard().getBottomRow(), players.get(pickerClient).getTribe());
        }
    }

    public void setID(int id) {
        this.lobbyID = id;
    }

    public void setModel(Game game){
        this.model = game;
    }

    public Game getModel() {
        return model;
    }

    public void startLobby(){
        Map<Integer, Tribe> tribes = new HashMap<>();

        for(VirtualClient client: players.keySet()){
            tribes.put(client.getID(), players.get(client).getTribe());
        }

        for(VirtualClient client: players.keySet()){
            client.startLobby(client.getID(), lobbyID, model.getBoard(), tribes);
        }

        model = new Game(getConfig(size), players.values());
    }

    public int getID(){

        return lobbyID;
    }

    public int getPlayerNum(){

        return size;
    }

    public Map<VirtualClient, Player> getPlayers(){

        return players;
    }

    public Tribe getPlayerTribe(VirtualClient client){

        return players.get(client).getTribe();
    }


    public void showRank(int clientID){
        List<Player> leaderBoard = model.getPlayers();
        Map<Integer, Integer> rank = new HashMap<>();

        leaderBoard.sort(null);

        for(int i = 0; i<leaderBoard.size(); i++){
           for(VirtualClient client: players.keySet()){
               if(players.get(client).getName() == leaderBoard.get(i).getName()){

                   rank.put(client.getID(), leaderBoard.get(i).getRank());
                   break;
               }
           }
        }

        for(VirtualClient client: players.keySet()){
            client.showRank(clientID, lobbyID, rank);
        }
    }
}
