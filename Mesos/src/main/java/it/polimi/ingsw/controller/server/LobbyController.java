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
    private int playerNum;
    private Game model;
    private Map<VirtualClient, Player> clients;


    public LobbyController(VirtualClient client, int lobbyID, int playerNum, String playerName, Totem totem){
        this.lobbyID = lobbyID;
        this.playerNum = playerNum;
        clients = new HashMap<>();

        addPlayer(client, playerName, totem);
    }


    public void addPlayer(VirtualClient client, String playerName, Totem totem) {
        playerNum++;

        clients.put(client, new Player(playerName, totem));
    }

    public void removePlayer(VirtualClient client) {
        playerNum--;

        clients.remove(client);
    }


    public void pickCards(VirtualClient client, List<Integer> topPicks, List<Integer> bottomPicks){
        List<Pickable> topPickCards = new ArrayList<>();
        List<Pickable> bottomPickCards = new ArrayList<>();
        Pickable foundCard;

        for(Integer pickedID: topPicks){
            foundCard = null;

            for(AbstractCharacter card: model.getBoard().getTopRow().getCharacterCards()){
                if(card.getID() == pickedID){

                    foundCard = card;
                    break;
                }
            }

            if(foundCard == null){
                for(AbstractBuilding card: model.getBoard().getTopRow().getBuildingCards()){
                    if(card.getID() == pickedID){

                        foundCard = card;
                        break;
                    }
                }
            }

            topPickCards.add(foundCard);
        }

        for(Integer pickedID: bottomPicks){
            foundCard = null;

            for(AbstractCharacter card: model.getBoard().getBottomRow().getCharacterCards()){
                if(card.getID() == pickedID){

                    foundCard = card;
                    break;
                }
            }

            if(foundCard == null){
                for(AbstractBuilding card: model.getBoard().getBottomRow().getBuildingCards()){
                    if(card.getID() == pickedID){

                        foundCard = card;
                        break;
                    }
                }
            }

            bottomPickCards.add(foundCard);
        }

        model.pick(clients.get(client), topPickCards, bottomPickCards);
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
        List<Player> players = new ArrayList<>();

        for(Player p: clients.values()){
            players.add(p);
        }

        model = new Game(getConfig(playerNum), players);
    }


    public int getID(){return lobbyID;}

    public int getPlayerNum(){return playerNum;}

    public Map<VirtualClient, String> getClients(){
        Map<VirtualClient, String>  clientNameMap= clients.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getName()));

        return clientNameMap;
    }

    public Tribe getPlayerTribe(VirtualClient client){

        return clients.get(client).getTribe();
    }


    public Map<Integer, Integer> getRank(){
        List<Player> leaderBoard = model.getPlayers();
        Map<Integer, Integer> rank = new HashMap<>();

        leaderBoard.sort(null);

        for(int i = 0; i<leaderBoard.size(); i++){
           for(VirtualClient client: clients.keySet()){
               if(clients.get(client).getName() == leaderBoard.get(i).getName()){

                   rank.put(client.getID(), leaderBoard.get(i).getRank());
                   break;
               }
           }
        }

        return rank;
    }
}
