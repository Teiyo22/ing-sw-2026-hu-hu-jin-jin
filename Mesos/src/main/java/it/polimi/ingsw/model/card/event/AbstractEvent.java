package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;

public abstract class AbstractEvent extends AbstractCard {
    public AbstractEvent(int era) {
        super(era);
    }

    public abstract void onEvent(Game game);

    @Override
    public void moveTo(Row row) {

    }
}
