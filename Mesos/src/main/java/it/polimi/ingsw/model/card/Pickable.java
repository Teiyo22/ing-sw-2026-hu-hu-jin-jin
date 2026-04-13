package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;

public interface Pickable {
    public abstract void onPick(Player player);

    public abstract void accept(CardVisitor visitor);

    public abstract void remove(Row row, int removedIndex);
}
