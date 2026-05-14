package it.polimi.ingsw.view.gui.components;

public abstract class SelectionListener<E> {
    protected boolean isEnabled;

    public SelectionListener() {
        this.isEnabled = false;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    abstract boolean onSelect(E element);  //boolean indicates whether the selection was successful

    abstract void onDeselect(E element);
}
