package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public class RoundEndState extends GameState {
    private final Row bottom;
    private final Row top;
    private final Deck deck;

    public RoundEndState(Game game, BuildingHandler buildingHandler) {

        super(game, buildingHandler);

        this.bottom = game.getBoard().getBottomRow();
        this.top = game.getBoard().getTopRow();
        this.deck = game.getBoard().getDeck();
    }

    @Override
    public void update() {

    }

    @Override
    public void onEnd(){
        if(deck.charEventCards()[2].isEmpty) {
            game.setGameState(new GameEndState);
        }else{
            game.setGameState(RoundStartState);
        }
    }

    private void resolveEvents() {
        List<Event> events = bottom.getEventCards();
        for(Event e: events){
            e.onEvent(game);
        }

        List<Sustenance> sustenance = bottom.getSustenanceEventCards();
        for(Sustenance s: sustenance){
            s.onEvent(game);
        }
    }

    private void setUp() {
        bottom.getEventCards().clear();
        bottom.getSustenanceEventCards().clear();
        bottom.getCharactersCards().clear();

        List<Character> characters = top.getCharacterCards();
        for(Character c: characters){
            c.moveTo(bottom);
        }
        List<Event> events = top.getEventCards();
        for(Event e: events){
            e.moveTo(bottom);
        }
        List<Sustenance> sustenance = top.getSustenanceEventCards();
        for(Sustenance s: sustenance){
            s.moveTo(bottom);
        }
        redrawCards();

    }

    private void redrawCards() {

        cards = deck.drawCards(players.size+4);
        for(AbstractCard card: cards) {
            if (card.era > deck.currentAge) {
                changeAge();
                deck.currentAge++;
            }
            card.moveTo(top);
        }
        onEnd();
    }

    private void changeAge() {
        bottom.getBuildingCards().clear();
    }
}
