package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class SelectableComponent<E> extends JLabel implements MouseListener {
    protected E element;
    protected final SelectionListener<SelectableComponent<E>> selectionListener;

    private Border innerBorder;
    private Border outerBorder;

    public abstract void render(E newElement);

    public SelectableComponent(E element, SelectionListener<SelectableComponent<E>> selectionListener) {
        this.element = element;
        this.selectionListener = selectionListener;
        this.addMouseListener(this);

        this.innerBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        this.outerBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (selectionListener != null)
            selectionListener.onSelect(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        outerBorder = BorderFactory.createRaisedBevelBorder();
        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        outerBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    public void setSelected(boolean selected) {
        innerBorder = selected ? BorderFactory.createLineBorder(Fonts.mesos_yellow, 3)
                : BorderFactory.createEmptyBorder(3, 3, 3, 3);
        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    public E getElement() {
        return element;
    }

    protected Dimension computeSize(Dimension baselineSize, Dimension imageSize) {
        int baselineWidth = baselineSize.width;
        int baselineHeight = baselineSize.height;

        int imageWidth = imageSize.width;
        int imageHeight = imageSize.height;

        double baselineRatio = (double) baselineWidth / baselineHeight;
        double imageRatio = (double) imageWidth / imageHeight;

        int finalWidth = baselineWidth;
        int finalHeight = baselineHeight;

        if (baselineRatio > imageRatio)
            finalWidth = (int) (baselineHeight * imageRatio);
        else
            finalHeight = (int) (baselineWidth / imageRatio);

        return new Dimension(finalWidth, finalHeight);
    }
}


