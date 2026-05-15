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
        Image frontImg = new ImageIcon(getClass().getResource("/images/front/" + resource + ".png")).getImage();
        Image backImg = new ImageIcon(getClass().getResource("/images/back/" + card.getEra() + ".png")).getImage();

        this.front = new ImageIcon(frontImg.getScaledInstance(120, 180, Image.SCALE_DEFAULT));
        this.back = new ImageIcon(backImg.getScaledInstance(120, 180, Image.SCALE_DEFAULT));

        this.setPreferredSize(new Dimension(120, 180));
    }

    public void renderFront(){
        this.setIcon(this.front);
    }

    public void renderBack(){
        this.setIcon(this.back);
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

