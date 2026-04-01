package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;

public abstract class AbstractCharacter extends AbstractCard implements Pickable {
    public AbstractCharacter(int era) {
        super(era);
    }

    @Override
    public void moveTo(Row row) {

    }
}
