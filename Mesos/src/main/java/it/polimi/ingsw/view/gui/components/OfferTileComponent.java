package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.section.OfferTrackSection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Map;

public class OfferTileComponent extends SelectableComponent<OfferTile> {
    private final Map<Totem, Image> totemIcons;
    private ImageIcon totem;

    public OfferTileComponent(OfferTile offer, OfferTrackSection selectionListener, Map<Totem, Image> totemIcons) {
        super(offer, selectionListener);
        this.totemIcons = totemIcons;
        this.totem = null;

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (screenSize.height * 0.3);
        int width = (int) (height * (2.0 / 3.0));

        Image img = new ImageIcon(getClass().getResource("/images/offerTiles/" + element.getType() + ".png")).getImage();
        this.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (totem != null) {
            totem.paintIcon(this, g,
                (int) (this.getSize().width * 0.3245),
                (int) (this.getSize().height * 0.21));
        }
    }

    @Override
    public void render(OfferTile offerTile) {
        element = offerTile;

        if (element.getAssignedPlayer() != null) {
            totem = new ImageIcon(totemIcons.get(element.getAssignedPlayer().getTotem())
                .getScaledInstance(
                    Math.max((int) (this.getSize().width * 0.3525), 1),
                    Math.max((int) (this.getSize().height * 0.125), 1),
                    Image.SCALE_DEFAULT
                ));
        } else {
            totem = null;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (element.getAssignedPlayer() == null) {
            super.mouseClicked(e);
        }
    }
}
