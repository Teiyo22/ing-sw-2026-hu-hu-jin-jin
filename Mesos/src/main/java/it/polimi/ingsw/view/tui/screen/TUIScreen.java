package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.utils.view.Formatter;
import it.polimi.ingsw.view.tui.action.Action;
import it.polimi.ingsw.view.tui.action.ActionRegistry;
import it.polimi.ingsw.view.tui.section.Section;

import java.util.List;

public abstract class TUIScreen implements Screen {
    protected final ClientController clientController;
    protected ActionRegistry registry;
    protected List<Section> sections;

    private ErrorMessage errorMsg = null;

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

        renderError();
        System.out.print("Enter action: ");

    }

    @Override
    public void showErrors(ErrorMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void handleInput(String input) {
        String[] args = input.split(" ");

        Action action = registry.resolve(args[0]);
        if (action == null || !action.parseAction(args))
            showErrors(new ErrorMessage("Input Error", "Invalid input"));
    }

    public void renderError() {
        if (errorMsg != null) {
            System.out.println();
            System.out.println(Formatter.separatorLine(errorMsg.getContext()));
            errorMsg.getErrors().stream().map(Formatter::line).forEach(System.out::println);
            System.out.println(Formatter.separatorLine(""));

            errorMsg = null;
        }
    }

}
