package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class OfferTileComponent extends SelectableComponent<OfferTile> {

    public OfferTileComponent(OfferTile offer, SelectionListener<OfferTile> selectionListener) {
        super(offer, selectionListener);
        this.setPreferredSize(new Dimension(180, 270));

        Image img = new ImageIcon(getClass().getResource("/images/offerTiles/" + element.getType() + ".png")).getImage();
        this.setIcon(new ImageIcon(img.getScaledInstance(180, 270, Image.SCALE_DEFAULT)));
    }

    public void deselect(){
        selected = false;
        updateBorder();
    }

}
