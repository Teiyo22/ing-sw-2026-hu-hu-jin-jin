package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.view.ImageCache;

import java.util.List;

public class TopRowSection extends RowSection {
    public TopRowSection(ImageCache imageCache, int maxCardCount) {
        super(imageCache, maxCardCount);
    }

    @Override
    public List<AbstractBuilding> getBuildings(Board board) {
        return board.getTopRow().getBuildingCards();
    }

    @Override
    public List<AbstractCharacter> getCharacters(Board board) {
        return board.getTopRow().getCharacterCards();
    }

    @Override
    public List<AbstractEvent> getEvents(Board board) {
        return board.getTopRow().getEventCards();
    }
}
