package it.polimi.ingsw.view.tui.action;

import java.util.Optional;

public interface Action {
    String key();
    String label();
    boolean isEnabled();
    Optional<String> parseAction(String[] args);
}
