package it.polimi.ingsw.view.tui.action;

public interface TUIAction {
    String key();
    String label();
    boolean isEnabled();
    boolean parseAction(String[] args);
}
