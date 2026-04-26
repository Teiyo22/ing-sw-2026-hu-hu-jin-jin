package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import javax.swing.*;
import java.util.List;
import java.util.*;

public class LobbyController {
    private int lobbyID;
    private int size;
    private Game model;
    private Map<VirtualClient, String> clients;
    private Map<Integer, Player> players;

    public LobbyController(VirtualClient client, int lobbyID, int playerNum, String playerName, Totem totem){
        this.lobbyID = lobbyID;
        this.size = playerNum;
        clients = new HashMap<>();
        players = new HashMap<>();

        addPlayer(client, playerName, totem);
    }


    public void addPlayer(VirtualClient client, String playerName, Totem totem) {
        size++;

        clients.put(client, playerName);
        players.put(client.getID(), new Player(playerName, totem));
    }

    public void removePlayer(VirtualClient client) {
        size--;

        clients.remove(client);
        players.remove(client.getID());
    }


    public void pickCards(int clientID, List<Integer> topPicks, List<Integer> bottomPicks){
        List<Pickable> topPickCards = new ArrayList<>();
        List<Pickable> bottomPickCards = new ArrayList<>();
        Pickable foundCard;

        for(Integer pickedID: topPicks){
            foundCard = null;

            for(AbstractCard card: model.getBoard().getTopRow().getCharacterCards()){
                if(card.getID() == pickedID){

                    foundCard = (Pickable)card;
                    break;
                }
            }

            if(foundCard == null){
                for(AbstractCard card: model.getBoard().getTopRow().getBuildingCards()){
                    if(card.getID() == pickedID){

                        foundCard = (Pickable)card;
                        break;
                    }
                }
            }

            topPickCards.add(foundCard);
        }

        for(Integer pickedID: bottomPicks){
            foundCard = null;

            for(AbstractCard card: model.getBoard().getBottomRow().getCharacterCards()){
                if(card.getID() == pickedID){

                    foundCard = (Pickable)card;
                    break;
                }
            }

            if(foundCard == null){
                for(AbstractCard card: model.getBoard().getBottomRow().getBuildingCards()){
                    if(card.getID() == pickedID){

                        foundCard = (Pickable)card;
                        break;
                    }
                }
            }

            bottomPickCards.add(foundCard);
        }

        model.pick(players.get(clientID), topPickCards, bottomPickCards);
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setModel(Game game){
        this.model = game;
    }

    public Game getModel() {
        return model;
    }

    public int getID(){return lobbyID;}

    public int getSize(){return size;}

    public Map<VirtualClient, String> getClients(){return clients;}

}
