package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;

import java.util.ArrayList;
import java.util.List;

public class CardPicksListener extends SelectionListener<AbstractCard> {
    private final List<Integer> picks;
    private int totalPicks;

    public CardPicksListener() {
        super();
        picks = new ArrayList<>();
        totalPicks = 0;
    }

    public List<Integer> getPicks() {
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
