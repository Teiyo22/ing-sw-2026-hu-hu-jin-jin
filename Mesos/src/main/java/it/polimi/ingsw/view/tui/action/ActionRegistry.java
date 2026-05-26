package it.polimi.ingsw.view.tui.action;

import java.util.ArrayList;
import java.util.List;

public class ActionRegistry {
    private final List<Action> actions = new ArrayList<>();

    public ActionRegistry register(Action a) {
        actions.add(a);
        return this;
    }

    public Action resolve(String cmd) {
        for (Action action : actions) {
            if ((action.key().equalsIgnoreCase(cmd) || action.label().equalsIgnoreCase(cmd)) && action.isEnabled())
                return action;
        }

        return null;
    }

    public List<Action> enabled() {
        return actions.stream().filter(Action::isEnabled).toList();
    }
}
