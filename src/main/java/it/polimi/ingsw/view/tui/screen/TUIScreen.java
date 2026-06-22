package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.controller.client.EventResult;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.utils.view.Formatter;
import it.polimi.ingsw.view.tui.action.TUIAction;
import it.polimi.ingsw.view.tui.action.ActionRegistry;
import it.polimi.ingsw.view.tui.section.TUISection;

import java.util.List;

public abstract class TUIScreen implements Screen {
    protected final ClientController clientController;
    protected ActionRegistry registry;
    protected List<TUISection> sections;

    private ErrorMessage errorMsg = null;
    protected List<EventResultMessage> eventResults = null;

    public TUIScreen(ClientController clientController) {
        this.clientController = clientController;
        this.eventResults = null;
    }

    /** The screen gets cleared and renders each visible section.*/
    @Override
    public void render() {
        synchronized (clientController) {
            Formatter.clearScreen();
            sections.stream()
                .filter(s -> s.isVisible(clientController))
                .forEach(s -> s.render(clientController));
        }

        renderError();
        renderEventResults();
        System.out.print("Enter action: ");

    }

    @Override
    public void showErrors(ErrorMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public void showEventResult(EventResultMessage eventResultMessage) {
        if (eventResults != null)
            eventResults.add(eventResultMessage);
    }

    /** Takes the first input as the requested action:
     * the action registry contains all the actions that are available,
     * the resolve method return the Action object associated with the input,
     * null if the action is not in this screen's registry, or disabled.
     * The remaining input is passed as arguments for the action, the parseAction interprets them and executes it
     * by calling the controller's methods.*/
    public void handleInput(String input) {
        String[] args = input.split(" ");

        errorMsg = null;
        if (eventResults != null)
            eventResults.clear();

        TUIAction action = registry.resolve(args[0]);
        if (action == null || !action.parseAction(args))
            showErrors(new ErrorMessage("Input Error", "Invalid input"));
    }

    private void renderError() {
        if (errorMsg != null) {
            System.out.println();
            System.out.println(Formatter.separatorLine(errorMsg.getContext()));
            errorMsg.getErrors().stream().map(Formatter::line).forEach(System.out::println);
            System.out.println(Formatter.separatorLine(""));
            System.out.println();
        }
    }

    private void renderEventResults() {
        if (eventResults != null)
            eventResults.forEach(this::renderEventResult);
    }

    private void renderEventResult(EventResultMessage eventResultMessage) {
        System.out.println();
        System.out.println(Formatter.separatorLine(eventResultMessage.getContext()));
        System.out.println(Formatter.line(String.format("%10s | %10s | %10s", "Player", "Food Delta", "PP Delta")));
        eventResultMessage.getResults().stream()
            .map(EventResult::toString)
            .map(Formatter::line)
            .forEach(System.out::println);
        System.out.println(Formatter.separatorLine(""));
        System.out.println();
    }
}
