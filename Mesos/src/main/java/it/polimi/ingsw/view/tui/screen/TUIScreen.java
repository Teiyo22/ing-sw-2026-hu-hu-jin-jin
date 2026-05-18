package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.tui.Formatter;
import it.polimi.ingsw.view.tui.action.ActionRegistry;
import it.polimi.ingsw.view.tui.section.Section;

import java.util.List;

public abstract class TUIScreen implements Screen {
    protected final ClientController clientController;
    protected ActionRegistry registry;
    protected List<Section> sections;

    private String errorMsg = "";

    public TUIScreen(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void render() {
        synchronized (clientController) {
            Formatter.clearScreen();
            sections.stream()
                    .filter(s -> s.isVisible(clientController))
                    .forEach(s -> s.render(clientController));
        }

        System.out.println("\u001B[1m\u001B[31m" + errorMsg + "\u001B[0m");
        System.out.print("Enter action: ");
    }

    @Override
    public void showError(String error) {
        errorMsg = error;
        render();
    }

    public void handleInput(String input) {
        String[] args = input.split(" ");

        if (args.length > 0)
            errorMsg = registry.resolve(args, clientController)
                    .flatMap(a -> a.parseAction(args))
                    .orElse("");
        else
            errorMsg = "Missing input";
    }
}
