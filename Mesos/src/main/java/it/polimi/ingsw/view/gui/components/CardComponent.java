package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.view.gui.util.CardCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CardComponent extends SelectableComponent<AbstractCard> {
    private final ImageIcon front;
    private final ImageIcon back;

    private final Timer flipTimer;

    public CardComponent(AbstractCard card, SelectionListener<AbstractCard> selectionListener, CardCache cache) {
        super(card, selectionListener);

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        this.front = cache.getFront("/images/front/" + card.getResource() + ".png");
        this.back = cache.getBack("/images/back/" + card.getEra() + ".png");

        this.setIcon(front);
        this.setPreferredSize(new Dimension(front.getIconWidth()+6, front.getIconHeight()+6));

        flipTimer = new Timer(200, e -> this.renderBack());
        flipTimer.setRepeats(false);
    }

    public void renderFront(){
        this.setIcon(this.front);
    }

    public void renderBack(){
        this.setIcon(this.back);
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

