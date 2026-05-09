package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CardComponent extends SelectableComponent<AbstractCard> {
    private final ImageIcon front;
    private final ImageIcon back;

    public CardComponent(AbstractCard card, SelectionListener<AbstractCard> selectionListener) {
        super(card, selectionListener);

        String resource = card.getResource();
        String era = String.valueOf(card.getEra());
        Image frontImg = new ImageIcon("src.main.java.it.polimi.ingsw.utils.images.cards.front."+resource).getImage();
        Image backImg = new ImageIcon("src.main.java.it.polimi.ingsw.utils.images.cards.back."+era).getImage();
        this.front = new ImageIcon(frontImg.getScaledInstance(100, 150, Image.SCALE_DEFAULT));
        this.back = new ImageIcon(backImg.getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    }

    public void renderFront(){
        this.setIcon(this.front);
        this.addMouseListener(this);
    }

    public void renderBack(){
        this.setIcon(this.back);
        this.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.renderBack();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.renderFront();
    }
}

