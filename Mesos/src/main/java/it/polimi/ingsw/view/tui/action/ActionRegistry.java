package it.polimi.ingsw.view.tui.action;

import java.util.ArrayList;
import java.util.List;

public class ActionRegistry {
    private final List<TUIAction> actions = new ArrayList<>();

    public ActionRegistry register(TUIAction a) {
        actions.add(a);
        return this;
    }

    public TUIAction resolve(String cmd) {
        for (TUIAction action : actions) {
            if ((action.key().equalsIgnoreCase(cmd) || action.label().equalsIgnoreCase(cmd)) && action.isEnabled())
                return action;
        }

        return null;
    }

    public List<TUIAction> enabled() {
        return actions.stream().filter(TUIAction::isEnabled).toList();
    }
}
