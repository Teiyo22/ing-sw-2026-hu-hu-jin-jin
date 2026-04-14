package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.board.Board;

import java.util.Collections;
import java.util.List;
import java.lang.Math;

public class GameStartState extends GameState{
    private int playerCount = 0;

    public GameStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    @Override
    public void update() {
        playerCount++;

        if(playerCount == game.getPlayerConfig().getNum()) {
            assignPlayersToOrderTile();
            distributeCards();
            game.setGameState(new RoundStartState(game, buildingHandler));
            game.getGameState().update();
        }
    }

    private void assignPlayersToOrderTile(){
        List<Player> players = game.getPlayers();
        OrderSlot[] orderTile = game.getBoard().getOrderTile();

        Collections.shuffle(players);

        for(int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            p.setFood(2 + (i + 1) / 2);
            orderTile[i].setAssignedPlayer(p); ;
        }
    }

    private void distributeCards() {
        List<AbstractCard> cards;
        Row topRow =  game.getBoard().getTopRow();
        Row bottomRow = game.getBoard().getBottomRow();
        int ID = 0;

        int drawCount = game.getPlayerConfig().getNum() + 4;
        do {
            cards = game.getBoard().getDeck().drawCards(drawCount);
            for(AbstractCard card: cards){
                card.setID(ID);
                ID++;
                card.moveTo(bottomRow);
            }

            for(AbstractEvent event: bottomRow.getEventCards())
                event.moveTo(topRow);

            for(Sustenance event: bottomRow.getSustenanceEventCards())
                event.moveTo(topRow);

            bottomRow.getEventCards().clear();
            bottomRow.getSustenanceEventCards().clear();

            drawCount = game.getPlayerConfig().getNum() + 4 - bottomRow.getCharacterCards().size();
        } while(drawCount > 0);

        drawCount = game.getPlayerConfig().getNum() + 1 -
                    topRow.getEventCards().size() -
                    topRow.getSustenanceEventCards().size();

        cards = game.getBoard().getDeck().drawCards(drawCount);
        for(AbstractCard card: cards){
            card.setID(ID);
            ID++;
            card.moveTo(topRow);
        }


        cards = game.getBoard().getDeck().drawBuildingCards();
        for(AbstractCard card: cards){
            card.setID(ID);
            ID++;
            card.moveTo(game.getBoard().getTopRow());
        }
    }
}
