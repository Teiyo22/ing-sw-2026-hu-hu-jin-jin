package it.polimi.ingsw.view.gui.components;

public interface SelectionListener<E extends SelectableComponent<?>> {
    void onSelect(E element);
    void resetSelection();
}
