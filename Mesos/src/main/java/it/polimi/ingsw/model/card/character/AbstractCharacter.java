package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;

public abstract class AbstractCharacter extends AbstractCard implements Pickable {
    public AbstractCharacter(String type, int era, boolean isFinal) {
        super(type, era, isFinal);
    }

    public AbstractCharacter(AbstractCharacter source) {
        super(source);
    }

    @Override
    public void moveTo(Row row) {

    }
}
