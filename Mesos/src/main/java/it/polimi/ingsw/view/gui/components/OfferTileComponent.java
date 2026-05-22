package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.player.Totem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Map;

public class OfferTileComponent extends SelectableComponent<OfferTile> {
    private final int index;
    private final Map<Totem, Image> totemIcons;
    private ImageIcon totem;

    public OfferTileComponent(OfferTile offer, SelectionListener<OfferTile> selectionListener, int index, Map<Totem, Image> totemIcons) {
        super(offer, selectionListener);
        this.index = index;
        this.totemIcons = totemIcons;

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = (int) (screenSize.height * 0.3);
        int width = (int) (height * (2.0 / 3.0));

        Image img = new ImageIcon(getClass().getResource("/images/offerTiles/" + element.getType() + ".png")).getImage();
        this.setIcon(new ImageIcon(img.getScaledInstance(width - 6, height - 6, Image.SCALE_DEFAULT)));
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (totem != null) {
            totem.paintIcon(this, g, 61, 50);
        }
    }

    public int getIndex() {
        return index;
    }

    public void update(ClientController clientController) {
        element = clientController.getBoard().getOfferTrack()[index];

        if (element == null || element.getAssignedPlayer() == null) {
            deselect();
            totem = null;
        } else {
            totem = new ImageIcon(totemIcons.get(element.getAssignedPlayer().getTotem()));
        }
    }

    public void deselect() {
        selected = false;
        updateBorder();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (element.getAssignedPlayer() == null) {
            super.mouseClicked(e);
        }
    }
}
