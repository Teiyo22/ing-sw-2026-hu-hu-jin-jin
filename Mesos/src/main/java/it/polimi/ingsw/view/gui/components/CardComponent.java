package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.view.gui.section.RowSection;
import it.polimi.ingsw.view.gui.util.CardCache;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CardComponent extends SelectableComponent<AbstractCard> {
    private ImageIcon front;
    private ImageIcon back;

    private final CardCache cache;
    private final Timer flipTimer;

    public CardComponent(RowSection selectionListener, CardCache cache) {
        super(null, selectionListener);
        this.front = null;
        this.back = null;

        this.cache = cache;
        flipTimer = new Timer(200, e -> this.renderBack());
        flipTimer.setRepeats(false);

        this.setVisible(false);
    }

    public void renderFront(){
        this.setIcon(this.front);
    }

    public void render(AbstractCard card){
        this.element = card;

        if (card != null) {
            front = cache.getFront("/images/front/" + card.getResource() + ".png");
            back = cache.getBack("/images/back/" + card.getEra() + ".png");
            setIcon(front);
            setVisible(true);
        } else {
            setVisible(false);
        }
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

