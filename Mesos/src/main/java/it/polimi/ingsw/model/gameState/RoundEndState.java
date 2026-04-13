package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Deck;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;

import java.util.List;

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
            game.setGameState(new RoundStartState(game, buildingHandler));

        }else
            game.setGameState(new GameEndState(game, buildingHandler));

        game.getGameState().update();
    }

    private void resolveEvents() {
        List<AbstractEvent> events = bottom.getEventCards();
        for(AbstractEvent e: events){
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
        bottom.getCharacterCards().clear();

        List<AbstractCharacter> characters = top.getCharacterCards();
        for(AbstractCharacter c: characters){
            c.moveTo(bottom);
        }
        top.getCharacterCards().clear();

        List<AbstractEvent> events = top.getEventCards();
        for(AbstractEvent e: events){
            e.moveTo(bottom);
        }
        top.getEventCards().clear();

        List<Sustenance> sustenance = top.getSustenanceEventCards();
        for(Sustenance s: sustenance){
            s.moveTo(bottom);
        }
        top.getSustenanceEventCards().clear();

        redrawCards();

    }

    private void redrawCards() {

        List<AbstractCard> cards = deck.drawCards(game.getPlayers().size+4);
        for(AbstractCard card: cards) {
            if (card.getEra() > deck.getCurrentEra()) {
                bottom.getBuildingCards().clear();
                deck.changeEra();
                List<AbstractBuilding> buildingList = deck.drawBuildingCards();
                for(AbstractBuilding building: buildingList){
                    building.moveTo(top);
                }
            }
            card.moveTo(top);
        }
    }

}
