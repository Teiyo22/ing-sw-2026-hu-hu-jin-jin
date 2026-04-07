package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.board.Board;

public class GameStartState extends GameState{
    private int playerCount = 0;
    private OrderSlot[] orderTile;
    private List<Player> playerList;

    public GameStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

        this.playerList = game.getPlayers();
        this.orderTile = game.getBoard().getOrderTile();
    }

    @Override
    public void update() {
        playerCount++;
        if(playerCount == playerList.size()) {
            onEnd();
        }
    }

    @Override
    public void onEnd(){
        int player_index = 1;
        int food = 2;
        List<AbstractCard> cards;

        assignPlayersToOrderTile();
        Collections.shuffle(orderTile);
        for(Player p: orderTile) {
            p.setFood(food);
            player_index++;
            food = 2 + Math.floor(player_index);
        }

        cards = game.getBoard().getDeck.drawCards(playersList.size+1);
        distributeCards(cards);

        cards = game.getBoard().getDeck.drawCards(playersList.size+4);
        distributeCards(cards);

        game.setGameState(new RoundStartState);
    }

    private void assignPlayersToOrderTile(){
        int i = 0;

        for(Player p: playersList) {
            orderTile[i].setPlayer(p);
            i++;
        }
    }

    private void distributeCards(List<AbstractCard> drawnCards) {
        Row row;

        if(drawnCards.size == playersList.size+1){
            row = game.getBoard().getBottomRow();
        }else
            row = game.getBoard().getTopRow();

        for(AbstractCard card: drawnCards){
            card.moveTo(row);
        }
    }
}
