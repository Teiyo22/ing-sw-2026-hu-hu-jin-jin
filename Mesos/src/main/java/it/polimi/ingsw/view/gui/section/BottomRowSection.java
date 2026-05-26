package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.view.ImageCache;

import java.util.List;
import java.util.stream.Stream;

public class BottomRowSection extends RowSection {
    public BottomRowSection(ImageCache imageCache, int maxCardCount) {
        super(imageCache, maxCardCount);
    }

    @Override
    public List<AbstractBuilding> getBuildings(Board board) {
        return board.getBottomRow().getBuildingCards();
    }

    @Override
    public List<AbstractCharacter> getCharacters(Board board) {
        return board.getBottomRow().getCharacterCards();
    }

    @Override
    public List<AbstractEvent> getEvents(Board board) {
        return Stream.concat(
            board.getBottomRow().getEventCards().stream(),
            board.getBottomRow().getSustenanceEventCards().stream()
        ).toList();
    }
}
