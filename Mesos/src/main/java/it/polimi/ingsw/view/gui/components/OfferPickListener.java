package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;

import java.util.ArrayList;
import java.util.List;

public class OfferPickListener extends SelectionListener<OfferTile> {
    private final List<OfferTileComponent> components;
    private OfferTile selected;

    public OfferPickListener() {
        components = new ArrayList<>();
        selected = null;
    }

    public int getSelectedOfferIndex() {
        for ( OfferTileComponent component : components ) {
            if(component.getElement().equals(selected)) {
                return component.getIndex();
            }
        }

        return -1;
    }

    public void resetPick() {
        selected = null;
    }

    @Override
    public boolean onSelect(OfferTile offerTile) {
        if(isEnabled) {
            selected = offerTile;
            for(OfferTileComponent component : components) {
                component.deselect();
            }
            return true;
        } else return false;
    }

    @Override
    public void onDeselect(OfferTile offerTile) {
        if(isEnabled) {
            selected = null;
        }
    }

    public void addComponent(OfferTileComponent offerTileComponent) {
        components.add(offerTileComponent);
    }

    public List<OfferTileComponent> getComponents() {
        return components;
    }
}
