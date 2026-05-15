package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.AbstractCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CardComponent extends SelectableComponent<AbstractCard> {
    private final ImageIcon front;
    private final ImageIcon back;

    private final Timer flipTimer;

    public CardComponent(AbstractCard card, SelectionListener<AbstractCard> selectionListener) {
        super(card, selectionListener);

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = (int) (screenSize.height * 0.22);
        int width = (int) (height * (2.0 / 3.0));

        String resource = card.getResource();
        Image frontImg = new ImageIcon(getClass().getResource("/images/front/" + resource + ".png")).getImage();
        Image backImg = new ImageIcon(getClass().getResource("/images/back/" + card.getEra() + ".png")).getImage();

        this.front = new ImageIcon(frontImg.getScaledInstance(width - 6, height - 6, Image.SCALE_DEFAULT));
        this.back = new ImageIcon(backImg.getScaledInstance(width - 6, height - 6, Image.SCALE_DEFAULT));

        this.setIcon(front);
        this.setPreferredSize(new Dimension(width, height));

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

