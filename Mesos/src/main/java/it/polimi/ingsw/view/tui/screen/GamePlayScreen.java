package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.tui.Formatter;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.tui.section.*;

import java.util.List;

public class GamePlayScreen implements Screen {
    private final ClientController clientController;
    private final ActionRegistry registry;
    private final List<Section> sections;

    private String errorMsg = "";

    public GamePlayScreen(ClientController clientController) {
        this.clientController = clientController;

        registry = new ActionRegistry()
                .register(new DisconnectAction(clientController))
                .register(new ShowAction(clientController))
                .register(new HideAction(clientController))
                .register(new PickOfferAction(clientController))
                .register(new PickCardAction(clientController))
                .register(new LeaveLobbyAction(clientController));

        sections = List.of(
                new ActionSection(registry),
                new PlayerInfoSection(),
                new OrderTileSection(),
                new RowSection(true),
                new OfferTrackSection(),
                new RowSection(false),
                new PlayerFocusSection()
        );
    }

    @Override
    public void render() {
        Formatter.clearScreen();

        synchronized (clientController) {
            sections.stream()
                    .filter(s -> s.isVisible(clientController))
                    .forEach(s -> s.render(clientController));
        }

        System.out.println(errorMsg);
        System.out.print("Enter action: ");
    }

    @Override
    public void handleInput(String input) {
        String[] args = input.split(" ");

        if (args.length > 0)
            errorMsg = registry.resolve(args, clientController)
                    .flatMap(a -> a.parseAction(args))
                    .orElse("");
        else
            errorMsg = "Missing input";
    }

    @Override
    public void showError(String error) {
        errorMsg = error;
        render();
    }
}
