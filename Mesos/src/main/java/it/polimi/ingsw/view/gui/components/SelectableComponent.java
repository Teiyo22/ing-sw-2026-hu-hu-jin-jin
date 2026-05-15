package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class SelectableComponent<E> extends JLabel implements MouseListener{
    protected E element;
    protected final SelectionListener<E> selectionListener;
    protected boolean selected;
    private boolean hover;

    private final Border selectionBorder;
    private final Border hoverBorder;
    private final Border emptyBorder;

    public SelectableComponent(E element, SelectionListener<E> selectionListener) {
        this.element = element;
        this.selectionListener = selectionListener;
        this.selected = false;
        this.addMouseListener(this);

        selectionBorder = BorderFactory.createLineBorder(Fonts.mesos_yellow, 3);
        hoverBorder = BorderFactory.createRaisedBevelBorder();
        emptyBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);

        this.setBorder(BorderFactory.createCompoundBorder(emptyBorder, emptyBorder));
    }

    public void updateBorder(){
        Border innerBorder;
        Border outerBorder;

        if(selected){
            innerBorder = selectionBorder;
        } else {
            innerBorder = emptyBorder;
        }

        if(hover){
            outerBorder = hoverBorder;
        } else {
            outerBorder = emptyBorder;
        }

        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(selectionListener !=null){
            if(selected){
                selectionListener.onDeselect(element);
                selected = false;
                updateBorder();
            } else {
                selected = selectionListener.onSelect(element);
                updateBorder();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(selectionListener !=null){
            hover = true;
            updateBorder();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(selectionListener !=null){
            hover = false;
            updateBorder();
        }
    }

    public E getElement() {
        return element;
    }
}


