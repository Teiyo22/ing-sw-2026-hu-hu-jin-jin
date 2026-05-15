package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;

import java.util.HashSet;
import java.util.Set;

public class CardPicksListener extends SelectionListener<AbstractCard> {
    private final Set<Integer> picks;
    private int totalPicks;

    public CardPicksListener() {
        super();
        picks = new HashSet<>();
        totalPicks = 0;
    }

    public Set<Integer> getPicks() {
        return picks;
    }

    public void setTotalPicks(int totalPicks) {
        this.totalPicks = totalPicks;
    }

    @Override
    public boolean onSelect(AbstractCard card) {
        if (isEnabled && picks.size() < totalPicks) {
            picks.add(card.getID());
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onDeselect(AbstractCard card) {
        if (isEnabled) {
            picks.remove(card.getID());
        }
    }
}
