package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;

public abstract class AbstractEvent extends AbstractCard {
    public AbstractEvent(int era, boolean isFinal) {
        super(era, isFinal);
    }

    public AbstractEvent(AbstractEvent source) {
        super(source);
    }

    /**
     * Applies the specific event effects to the game.
     *
     * @param game is used to get the reference to the players.
     * */
    public abstract void onEvent(Game game);

    @Override
    public void moveTo(Row row){
        row.addEvent(this);
    }
}
