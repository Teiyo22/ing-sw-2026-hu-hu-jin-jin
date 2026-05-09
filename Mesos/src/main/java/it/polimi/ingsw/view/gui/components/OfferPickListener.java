package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;

import javax.swing.*;
import java.util.Map;

public class OfferPickListener extends SelectionListener<OfferTile> {
    private final Map<OfferTile, Integer> offerIndexes;
    private int selectedOfferIndex;

    public OfferPickListener(Map<OfferTile, Integer> offerIndexes) {
        this.offerIndexes = offerIndexes;
        selectedOfferIndex = -1;
    }

    public int getSelectedOfferIndex() {
        return selectedOfferIndex;
    }

    @Override
    public boolean onSelect(OfferTile offerTile) {
        if(isEnabled) {
            selectedOfferIndex = offerIndexes.get(offerTile);
            return true;
        } else return false;
    }

    @Override
    public void onDeselect(OfferTile offerTile) {
        if(isEnabled) {
            selectedOfferIndex = -1;
        }
    }
}
