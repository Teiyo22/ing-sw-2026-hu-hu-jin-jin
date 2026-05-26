package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.gameState.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.AbstractCard;

import java.util.Collections;
import java.util.List;

public class GameStartState extends GameState{
    public GameStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    /**
     * Starts the game by assigning each player to the order tile and distributing cards from the deck to the rows.
     * Then, the state changes to {@link RoundStartState}.
     * */
    @Override
    public void update() {
        assignPlayersToOrderTile();
        distributeCards();
        game.setGameState(new RoundStartState(game, buildingHandler));
        game.getGameState().update();
    }

    /**
     * Randomly assigns each player to the order tile and initializes their tribe with the correct amount of food.
     * */
    private void assignPlayersToOrderTile(){
        List<Player> players = game.getPlayers();
        OrderSlot[] orderTile = game.getBoard().getOrderTile();

        Collections.shuffle(players);

        for(int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            int initialFood = 2 + (i + 1) / 2;

            p.initTribe(initialFood);
            orderTile[i].setPlayer(p);
        }
    }

    /**
     * Distributes the cards from the deck starting from the bottom row.
     * All drawn event cards are moved to the top row.
     * The total starting number of character/event cards for each row depends on the number of players.
     * Buildings are only drawn for the top row.
     * */
    private void distributeCards() {
        List<AbstractCard> cards;
        Row topRow =  game.getBoard().getTopRow();
        Row bottomRow = game.getBoard().getBottomRow();
        int ID = 0;

        int drawCount = game.getPlayerConfig().getNum() + 1;
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

            drawCount = game.getPlayerConfig().getNum() + 1 - bottomRow.getCharacterCards().size();
        } while(drawCount > 0);

        drawCount = game.getPlayerConfig().getNum() + 4 - topRow.getEventCards().size() - topRow.getSustenanceEventCards().size();

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

    @Override
    public ModelStateInfo getModelStateInfo() {
        return null;
    }
}
