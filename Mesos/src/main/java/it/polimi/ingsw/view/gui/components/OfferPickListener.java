package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OfferPickListener extends SelectionListener<OfferTile> {
    private final Map<OfferTile, Integer> offerIndexes;
    private final List<OfferTileComponent> components;
    private int selectedOfferIndex;

    public OfferPickListener(Map<OfferTile, Integer> offerIndexes) {
        this.offerIndexes = offerIndexes;
        selectedOfferIndex = -1;

        components = new ArrayList<>();
    }

    public int getSelectedOfferIndex() {
        return selectedOfferIndex;
    }

    @Override
    public boolean onSelect(OfferTile offerTile) {
        if(isEnabled) {
            selectedOfferIndex = offerIndexes.get(offerTile);
            for(OfferTileComponent component : components) {
                component.deselect();
            }
            return true;
        } else return false;
    }

    @Override
    public void onDeselect(OfferTile offerTile) {
        if(isEnabled) {
            selectedOfferIndex = -1;
        }
    }

    public void addCompoent(OfferTileComponent offerTileComponent) {
        components.add(offerTileComponent);
    }
}
