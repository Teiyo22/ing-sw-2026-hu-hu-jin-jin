package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.view.gui.section.RowSection;
import it.polimi.ingsw.view.gui.util.ImageCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class CardComponent extends SelectableComponent<AbstractCard> {
    private Image cardImage;
    private final int maxCardCount;

    private final ImageCache cache;
    private final Timer flipTimer;

    public CardComponent(RowSection selectionListener, ImageCache cache, int maxCardCount) {
        super(null, selectionListener);
        cardImage = null;
        this.maxCardCount = maxCardCount;

        this.cache = cache;
        flipTimer = new Timer(200, e -> this.renderBack());
        flipTimer.setRepeats(false);

        this.setVisible(false);
    }

    public void render(AbstractCard card){
        this.element = card;

        if (cardImage == null)
            renderFront();

        setVisible(card != null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImage, 3, 3, getWidth() - 6, getHeight() - 6, this);
    }

    @Override
    public Dimension getPreferredSize() {
        if (cardImage != null && getParent() != null) {
            Container grandparent = getParent().getParent();
            if (grandparent != null && grandparent.getWidth() > 0) {
                return computeSize(
                    new Dimension(grandparent.getWidth() / maxCardCount, grandparent.getHeight()),
                    new Dimension(cardImage.getWidth(this), cardImage.getHeight(this))
                );
            }
        }
        return super.getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void renderFront() {
        cardImage = element == null ? null : cache.getImage("/images/front/" + element.getResource() + ".png");
        repaint();
    }

    public void renderBack() {
        cardImage = element == null ? null : cache.getImage("/images/back/" + element.getEra() + ".png");
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        flipTimer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (flipTimer.isRunning()) {
            flipTimer.stop();
            super.mouseReleased(e);
        } else {
            this.renderFront();
        }
    }
}

