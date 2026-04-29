package it.polimi.ingsw.controller.client.view.TUI;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.view.TUI.states.*;
import it.polimi.ingsw.controller.client.view.ViewStates;
import it.polimi.ingsw.controller.client.view.VirtualView;

import java.util.Scanner;

public class TUIView extends VirtualView implements Runnable {
    private ViewState state;
    private final Scanner scanner;

    public TUIView(ClientController clientController) {
        super(clientController);
        state = new ConnectionState(clientController);
        scanner = new Scanner(System.in);
    }

    @Override
    public void update() {
        state.render();
    }

    @Override
    public void transitionTo(ViewStates newState) {
        switch (newState) {
            case CONNECTION -> state = new ConnectionState(super.getClientController());
            case LOBBY_SELECTION ->  state = new LobbySelectionState(super.getClientController());
            case LOBBY_WAITING ->   state = new LobbyWaitingState(super.getClientController());
            case ROUND_START ->   state = new RoundStartState(super.getClientController());
            case ROUND_ACTION -> state  = new RoundActionState(super.getClientController());
            case GAME_END -> state  = new GameEndState(super.getClientController());
        }
    }

    @Override
    public void run() {
        state.render();
        while (true) {
            String input = scanner.nextLine();
            synchronized (this) {
                state.handleInput(input);
            }
        }
    }
}
