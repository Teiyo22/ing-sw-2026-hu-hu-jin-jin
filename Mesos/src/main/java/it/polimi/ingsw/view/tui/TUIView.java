package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.tui.screen.*;

import java.io.IOError;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TUIView implements View {
    private TUIScreen currScreen;
    private final ClientController clientController;

    private final AtomicBoolean running;
    private final AtomicBoolean update;
    private ScheduledExecutorService renderExecutor;

    public TUIView(ClientController clientController) {
        this.clientController = clientController;
        running = new AtomicBoolean(false);
        update = new AtomicBoolean(false);
        currScreen = null;
    }

    @Override
    public void start() {
        initRenderExecutor();
        running.set(true);

        transitionTo(ScreenType.LOGIN);
        try (Scanner scanner = new Scanner(System.in)) {
            while (running.get()) {
                currScreen.render();
                String input = scanner.nextLine().trim();
                currScreen.handleInput(input);
            }
        } catch (IOError e) {
            clientController.disconnect();
        }
    }

    @Override
    public void close() {
        running.set(false);
        shutdownRenderExecutor();
    }

    @Override
    public void displayError(ErrorMessage errorMsg) {
        currScreen.showErrors(errorMsg);
        notifyChange();
    }

    @Override
    public void displayEventResult(EventResultMessage eventResultMessage) {
        currScreen.showEventResult(eventResultMessage);
        notifyChange();
    }

    @Override
    public void notifyChange() {
        update.set(true);
    }

    @Override
    public void transitionTo(ScreenType type) {
        currScreen = getTUIScreen(type, clientController);
        update.set(true);
    }

    private void initRenderExecutor() {
        renderExecutor = Executors.newSingleThreadScheduledExecutor();
        renderExecutor.scheduleAtFixedRate(
                () -> {
                    if (running.get() && update.get()) {
                        update.set(false);
                        currScreen.render();
                    }
                },
                0, 100, TimeUnit.MILLISECONDS
        );
    }

    private void shutdownRenderExecutor() {
        renderExecutor.shutdown();

        try {
            if (!renderExecutor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                renderExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            renderExecutor.shutdownNow();
        }
    }

    public TUIScreen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOGIN -> new TUILoginScreen(clientController);
            case LOBBY_SELECTION -> new TUILobbySelectionScreen(clientController);
            case GAME_PLAY -> new TUIGamePlayScreen(clientController);
            case GAME_END -> new TUIGameEndScreen(clientController);
        };
    }
}
