package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.gameState.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Deck;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.BuildingHandler;
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

    /**
     * If the deck is not empty, resolves events and sets up the board for the next round.
     * Then changes the state to {@link RoundStartState}.
     * Else changes the state to {@link GameEndState}.
     * */
    @Override
    public void update() {
        if(!deck.getCharEventCards().isEmpty()) {
            resolveEvents();
            setUp();
            game.getLobbyState().notifyRoundEndUpdate();
            game.setGameState(new RoundStartState(game, buildingHandler));
        } else
            game.setGameState(new GameEndState(game, buildingHandler));

        game.getGameState().update();
    }

    /**
     * Resolves all events in the bottom row.
     * Sustenance events are solved last.
     * */
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

    /**
     * Moves all the cards from the top row to the bottom row and draws new cards for the latter.
     * */
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
        for(Sustenance s: sustenance)
            s.moveTo(bottom);
        top.getSustenanceEventCards().clear();

        redrawCards();
    }

    /**
     * Draws new cards from the deck for the top row.
     * If a card of the next era is drawn, then the buildings in the bottom row are discarded,
     * the buildings in the top row are moved to the bottom row, and new buildings are drawn for the top row.
     * */
    private void redrawCards() {
        List<AbstractCard> cards = deck.drawCards(game.getPlayers().size() + 4);
        int nextID = 0;

        for(AbstractCard card: cards) {
            if (card.getEra() > deck.getCurrentEra())
                resolveEraChange();

            card.setID(nextID);
            nextID++;
            card.moveTo(top);
        }
    }

    private void resolveEraChange() {
        int nextBuildingID = 10;

        bottom.getBuildingCards().clear();
        deck.changeEra();

        for(AbstractCard building: top.getBuildingCards())
            building.moveTo(bottom);
        top.getBuildingCards().clear();

        List<AbstractCard> buildings = deck.drawBuildingCards();
        for(AbstractCard building: buildings){
            building.setID(nextBuildingID);
            building.moveTo(top);
            nextBuildingID++;
        }
    }

    @Override
    public ModelStateInfo getModelStateInfo() {
        return null;
    }
}
