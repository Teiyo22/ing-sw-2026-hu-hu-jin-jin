package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;

import javax.swing.*;
import java.awt.*;

public class OfferTileComponent extends SelectableComponent<OfferTile> {

    public OfferTileComponent(OfferTile offer, SelectionListener<OfferTile> selectionListener) {
        super(offer, selectionListener);
    }

    public void render(){
        Image img = new ImageIcon("src.main.java.it.polimi.ingsw.utils.images.offers."+element.getType()).getImage();
        this.setIcon(new ImageIcon(img.getScaledInstance(150, 200, Image.SCALE_DEFAULT)));
    }
}
