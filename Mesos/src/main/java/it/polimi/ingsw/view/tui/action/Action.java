package it.polimi.ingsw.view.tui.action;

public interface Action {
    String key();
    String label();
    boolean isEnabled();
    boolean parseAction(String[] args);
}
