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

    public void render(AbstractCard card) {
        if (element != card) {
            element = card;
            renderFront();
        }

        setVisible(element != null);
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImage, 3, 3, getWidth() - 6, getHeight() - 6, this);
    }

    @Override
    public Dimension getPreferredSize() {
        if (cardImage != null && getParent() != null) {
            Container baseline;

            if (maxCardCount == 0) {
                baseline = getParent();
                return computeSize(
                    new Dimension(baseline.getWidth(), baseline.getHeight()),
                    new Dimension(cardImage.getWidth(this), cardImage.getHeight(this))
                );
            }

            baseline = getParent().getParent();
            if (baseline != null && baseline.getHeight() > 0) {
                return computeSize(
                    new Dimension(baseline.getWidth() / maxCardCount, baseline.getHeight()),
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

