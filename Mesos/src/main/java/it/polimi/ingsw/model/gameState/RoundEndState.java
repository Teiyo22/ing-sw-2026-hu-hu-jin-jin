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

        if(!deck.getCharEventCards().isEmpty()) {
            resolveEvents();
            setUp();
            game.setGameState(new RoundStartState);

        }else
            game.setGameState(new GameEndState);

        game.getGameState().update();
    }

    @Override
    public void onEnd(){

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
            if (card.era > deck.currentEra) {
                bottom.getBuildingCards().clear();
                deck.currentEra++;
                List<Building> buildingList = deck.drawBuildings();
                for(Building building: buildingList){
                    building.moveTo(top);
                }
            }
            card.moveTo(top);
        }

        onEnd();
    }

}
