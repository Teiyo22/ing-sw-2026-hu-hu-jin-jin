package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.utils.view.ImageCache;

import javax.swing.*;
import java.awt.*;

public class DeckComponent extends JLabel {
    private ImageCache imageCache;
    private JComponent sizeReference;

    private int era;

    public DeckComponent(JComponent sizeReference, ImageCache imageCache) {
        this.sizeReference = sizeReference;
        this.imageCache = imageCache;
        this.setVisible(true);
        this.era = 1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backImage = imageCache.getImage("/images/back/" + era + ".png");
        g.drawImage(backImage, 0, 0, sizeReference.getWidth() - 6, sizeReference.getHeight() - 6, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(sizeReference.getWidth(), sizeReference.getHeight());
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void setEra(int era) {
        this.era = era;
    }
}
