package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.view.gui.util.CardCache;

import java.util.List;

public class BottomRowSection extends RowSection {
    public BottomRowSection(CardCache cardCache) {
        super(cardCache);
    }

    @Override
    public List<AbstractCard> getBuildings(Board board) {
        return board.getBottomRow().getBuildingCards().stream()
                .map(b -> (AbstractCard) b)
                .toList();
    }

    @Override
    public List<AbstractCard> getCharacters(Board board) {
        return board.getBottomRow().getCharacterCards().stream()
                .map(c -> (AbstractCard) c)
                .toList();
    }

    @Override
    public List<AbstractCard> getEvents(Board board) {
        return board.getBottomRow().getEventCards().stream()
                .map(e -> (AbstractCard) e)
                .toList();
    }
}
