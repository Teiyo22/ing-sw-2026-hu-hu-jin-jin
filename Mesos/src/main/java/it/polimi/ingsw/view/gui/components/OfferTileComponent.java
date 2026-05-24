package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.view.gui.section.OfferTrackSection;
import it.polimi.ingsw.view.gui.util.ImageCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class OfferTileComponent extends SelectableComponent<OfferTile> {
    private final ImageCache imageCache;
    private final Image offerImage;

    public OfferTileComponent(OfferTile offerTile, OfferTrackSection selectionListener, ImageCache imageCache) {
        super(offerTile, selectionListener);
        this.imageCache = imageCache;
        offerImage = ImageCache.loadImage("/images/offerTiles/" + element.getType() + ".png");

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(offerImage, 3, 3, getWidth() - 6, getHeight() - 6, this);

        Dimension componentSize = getSize();
        if (element.getAssignedPlayer() != null) {
            g.drawImage(
                imageCache.getImage("/images/totems/" + element.getAssignedPlayer().getTotem() + ".png"),
                (int) (componentSize.width * 0.3245),
                (int) (componentSize.height * 0.21),
                (int) (componentSize.width * 0.3525),
                (int) (componentSize.height * 0.125),
                this
            );
        }

    }

    @Override
    public void render(OfferTile offerTile) {
        element = offerTile;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (element.getAssignedPlayer() == null) {
            super.mouseClicked(e);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Container parent = getParent();
        if (parent != null && parent.getWidth() > 0) {
            return computeSize(
                new Dimension(parent.getWidth() / 8, parent.getHeight()),
                new Dimension(offerImage.getWidth(this), offerImage.getHeight(this))
            );
        }
        return super.getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}