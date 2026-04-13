package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.board.Board;

import java.util.Collections;
import java.util.List;
import java.lang.Math;

public class GameStartState extends GameState{
    private int playerCount = 0;
    private OrderSlot[] orderTile;
    private List<Player> playersList;

    public GameStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

        this.orderTile = game.getBoard().getOrderTile();
    }

    @Override
    public void update() {
        Player p;

        playerCount++;

        if(playerCount == game.getGameSize()) {
            playersList = game.getPlayers();

            assignPlayersToOrderTile();

            distributeCards();

            game.setGameState(new RoundStartState(game, buildingHandler));
            game.getGameState().update();
        }
    }

    private void assignPlayersToOrderTile(){
        int i = 0;
        double player_index = 1.0;
        int food = 2;

        Collections.shuffle(playersList);
        for(Player p: playersList) {
            orderTile[i].setPlayer(p);
            p.setFood(food);
            food = 2 + (int)Math.floor(player_index);
            player_index+=0.5;
            i++;
        }
    }

    private void distributeCards() {
        List<AbstractCard> cards;

        cards = game.getBoard().getDeck().drawCards(playersList.size()+1);
        for(AbstractCard card: cards){
            card.moveTo(game.getBoard().getTopRow());
        }

        cards = game.getBoard().getDeck().drawCards(playersList.size()+4);
        for(AbstractCard card: cards){
            card.moveTo(game.getBoard().getBottomRow());
        }

        List<AbstractBuilding> buildingList = game.getBoard().getDeck().drawBuildingCards();
        for(AbstractBuilding building: buildingList){
            building.moveTo(game.getBoard().getTopRow());
        }
    }
}
