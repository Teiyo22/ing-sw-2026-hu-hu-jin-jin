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

        assignPlayersToOrderTile();
        for(Player p: orderTile) {
            p.setFood(food);
            player_index++;
            food = 2 + Math.floor(player_index);
        }

        distributeCards();

        game.setGameState(new RoundStartState());
        game.getGameState().update();
    }

    private void assignPlayersToOrderTile(){
        int i = 0;
        Collections.shuffle(playerList);
        for(Player p: playersList) {
            orderTile[i].setPlayer(p);
            i++;
        }
    }

    private void distributeCards() {
        Row row;
        List<AbstractCard> cards;

        cards = game.getBoard().getDeck().drawCards(playersList.size()+1);
        for(AbstractCard card: cards){
            card.moveTo(row);
        }

        cards = game.getBoard().getDeck().drawCards(playersList.size()+4);
        for(AbstractCard card: cards){
            card.moveTo(row);
        }
    }
}
