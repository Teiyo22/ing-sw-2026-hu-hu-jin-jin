package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.tui.Formatter;
import it.polimi.ingsw.view.tui.section.ActionSection;
import it.polimi.ingsw.view.tui.section.LobbyInfoSection;
import it.polimi.ingsw.view.tui.section.LobbyListSection;
import it.polimi.ingsw.view.tui.section.Section;

import java.util.List;


public class LobbySelectionScreen implements Screen {
    private final ClientController clientController;
    private final ActionRegistry registry;
    private final List<Section> sections;

    private String errorMsg = "";


    public LobbySelectionScreen(ClientController clientController) {
        this.clientController = clientController;

        registry = new ActionRegistry()
                .register(new DisconnectAction(clientController))
                .register(new GetWaitingLobbiesAction(clientController))
                .register(new CreateLobbyAction(clientController))
                .register(new LobbyInfoAction(clientController))
                .register(new JoinLobbyAction(clientController))
                .register(new LeaveLobbyAction(clientController))
                .register(new StartLobbyAction(clientController));

        sections = List.of(
                new ActionSection(registry),
                new LobbyInfoSection(),
                new LobbyListSection()
        );
    }

    @Override
    public void render() {
        Formatter.clearScreen();
        synchronized (clientController) {
            sections.forEach(s -> s.render(clientController));
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
