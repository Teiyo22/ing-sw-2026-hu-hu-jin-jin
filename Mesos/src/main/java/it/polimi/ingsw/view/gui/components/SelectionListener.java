package it.polimi.ingsw.view.gui.components;

public abstract class SelectionListener<E> {
    protected boolean isEnabled;

    public SelectionListener() {
        this.isEnabled = false;
    }

    public void enable() {
        isEnabled = true;
    }

    public void disable() {
        isEnabled = false;
    }

    abstract boolean onSelect(E element);  //boolean indicates whether the selection was successful

    abstract void onDeselect(E element);
}
