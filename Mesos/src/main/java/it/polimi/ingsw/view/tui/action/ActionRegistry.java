package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionRegistry {
    private final List<Action> actions = new ArrayList<>();

    public ActionRegistry register(Action a) {
        actions.add(a);
        return this;
    }

    public Optional<Action> resolve(String[] args, ClientController clientController) {
        return actions.stream()
                .filter(a -> (
                        a.key().equalsIgnoreCase(args[0]) || a.label().equalsIgnoreCase(args[0])) &&
                        a.isEnabled())
                .findFirst();
    }

    public List<Action> enabled() {
        return actions.stream().filter(Action::isEnabled).toList();
    }
}
