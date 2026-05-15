package it.polimi.ingsw.view.gui.components;

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

    public SelectableComponent(E element, SelectionListener<E> selectionListener) {
        this.element = element;
        this.selectionListener = selectionListener;
        this.selected = false;
        this.addMouseListener(this);
    }

    public void updateBorder(){
        Border innerBorder;
        Border outerBorder;

        if(selected){
            innerBorder = BorderFactory.createLineBorder(Color.WHITE, 2);
        } else {
            innerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        }

        if(hover){
            outerBorder = BorderFactory.createRaisedBevelBorder();
        } else {
            outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
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


