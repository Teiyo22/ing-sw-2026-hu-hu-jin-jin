package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class OfferTileComponent extends SelectableComponent<OfferTile> {
    private final int index;

    public OfferTileComponent(OfferTile offer, SelectionListener<OfferTile> selectionListener, int index) {
        super(offer, selectionListener);
        this.index = index;

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = (int) (screenSize.height * 0.35);
        int width = (int) (height * (2.0 / 3.0));

        Image img = new ImageIcon(getClass().getResource("/images/offerTiles/" + element.getType() + ".png")).getImage();
        this.setIcon(new ImageIcon(img.getScaledInstance(width - 6, height - 6, Image.SCALE_DEFAULT)));
        this.setPreferredSize(new Dimension(width, height));
    }

    public int getIndex() {
        return index;
    }

    public void update(ClientController clientController) {
        element = clientController.getCurrLobby().getBoard().getOfferTrack()[index];
        if(element.getAssignedPlayer()==null)
            deselect();
    }

    public void deselect(){
        selected = false;
        updateBorder();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(element.getAssignedPlayer() == null) {
            super.mouseClicked(e);
        }
    }
}
