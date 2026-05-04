package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.command.*;

import java.util.Map;

public class LobbySelectionScreen implements Screen {
    private final ClientController clientController;
    private Map<Integer, Lobby> waitingLobbies;
    private Lobby currLobby;

    public LobbySelectionScreen(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void render() {
        clearScreen();
        printAvailableActions();
        printLobbyInfo();
        printLobbyList();

        System.out.println("+===================================================================================================================+");
        System.out.println();
        System.out.println("Enter action: ");
    }

    @Override
    public void handleInput(String input) {
        switch (input.toLowerCase()) {
            case "0", "quit" -> new QuitCommand(clientController).execute();
            case "1", "list" -> new GetWaitingLobbiesCommand(clientController).execute();
            case "2", "create" -> handleCreate();
            case "3", "info" -> handleInfo();
            case "4", "join" -> handleJoin();
            case "5", "leave" -> handleLeave();
        }
    }

    @Override
    public void onEnter() {
        waitingLobbies = clientController.getWaitingLobbies();
        currLobby = clientController.getCurrLobby();
    }

    @Override
    public void onExit() {

    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J"); // Terminal must support ANSI escape sequences for this to work.
        System.out.flush();
    }

    private void printAvailableActions() {
        System.out.println("+================================================= Lobby Selection =================================================+");
        System.out.println("| Available actions:                                                                                                |");
        System.out.println("| 0. Quit                                                                                                           |");
        System.out.println("| 1. List                                                                                                           |");
        System.out.println("| 2. Create                                                                                                         |");

        if (waitingLobbies != null && !waitingLobbies.isEmpty())
            System.out.println("| 3. Info                                                                                                           |");

        if (currLobby != null) {
            System.out.println("| 4. Join                                                                                                           |");
            System.out.println("| 5. Leave                                                                                                          |");
        }
    }

    private void printLobbyList() {
        if (waitingLobbies == null || waitingLobbies.isEmpty())
            return;

        System.out.println("+===============+=================================== Lobby List ====================================================+");
        for (Lobby lobby : waitingLobbies.values())
            System.out.printf("| Lobby ID: %3d | Size: %3d                                                                                         |\n", lobby.getLobbyID(), lobby.getSize());
    }

    private void printLobbyInfo() {
        if (currLobby == null)
            return;

        System.out.println("+================================================== Lobby Info ====================================================+");
        System.out.printf("| Lobby ID:     %3d                                                                                                |\n", currLobby.getLobbyID());
        System.out.printf("| Player Count: %3d/%3d                                                                                            |\n", currLobby.getPlayerCount(), currLobby.getSize());
        System.out.print("| Players:                                                                                                         |\n");
        for (Player player : currLobby.getPlayers().values())
            System.out.printf("| - %s%20s%s                                                                                         |\n", player.getTotem().getColor(), player.getName(), "\u001B[0m");
    }

    private void handleCreate() {
        int lobbySize = -1;
        String playerName;
        Totem totem = null;

        System.out.println("Enter 'q' to cancel");

        do {
            System.out.println("Enter lobby size (2-5): ");
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("q")) {
                render();
                return;
            }

            try {
                lobbySize = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                lobbySize = -1;
                System.out.println("Invalid input: lobby size must be a number.");
            }

        } while (lobbySize < 2 || lobbySize > 5);

        System.out.println("Enter player name: ");
        playerName = System.console().readLine();

        if (playerName.equalsIgnoreCase("q")) {
            render();
            return;
        }

        do {
            System.out.println("Choose a totem (RED|BLUE|WHITE|BLACK|YELLOW): ");
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("q")) {
                render();
                return;
            }

            try {
                totem = Totem.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: totem must be one of RED, BLUE, WHITE, BLACK, YELLOW.");
            }
        } while (totem == null);

        new CreateLobbyCommand(clientController, lobbySize, new Player(playerName, totem)).execute();
    }

    private void handleInfo() {
        int lobbyID = -1;

        if (waitingLobbies == null || waitingLobbies.isEmpty()) {
            System.out.println("Invalid command, enter anything to continue: ");
            System.console().readLine();
            render();
            return;
        }
        System.out.println("Enter 'q' to cancel");

        do {
            System.out.println("Enter lobby ID: ");

            String input = System.console().readLine();

            if (input.equalsIgnoreCase("q")) {
                render();
                return;
            }

            try {
                lobbyID = Integer.parseInt(input);

                if (waitingLobbies.containsKey(lobbyID))
                    new LobbyInfoCommand(clientController, lobbyID).execute();
                else {
                    System.out.println("Invalid lobby ID.");
                    lobbyID = -1;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input: lobby size must be a number.");
            }

        } while (lobbyID == -1);
    }

    private void handleJoin() {
        String playerName;
        Totem totem;

        if (currLobby == null) {
            System.out.println("Invalid command, enter anything to continue: ");
            System.console().readLine();
            render();
            return;
        }

        System.out.println("Enter 'q' to cancel");

        do {
            System.out.println("Enter player name: ");
            playerName = System.console().readLine();

            if (playerName.equalsIgnoreCase("q")) {
                render();
                return;
            }

            if (!validateName(playerName)) {
                System.out.println("Invalid name, choose another one.");
                playerName = null;
            }
        } while (playerName == null);

        do {
            System.out.println("Choose a totem (RED|BLUE|WHITE|BLACK|YELLOW): ");
            String input = System.console().readLine();

            if (input.equalsIgnoreCase("q")) {
                render();
                return;
            }

            try {
                totem = Totem.valueOf(input.toUpperCase());

                if (!validateTotem(totem)) {
                    System.out.println("Invalid totem, choose another one.");
                    totem = null;
                }

            } catch (IllegalArgumentException e) {
                totem = null;
                System.out.println("Invalid input: totem must be one of RED, BLUE, WHITE, BLACK, YELLOW.");
            }
        } while (totem == null);

        new JoinLobbyCommand(clientController, currLobby.getLobbyID(), new Player(playerName, totem)).execute();
    }

    private boolean validateName(String name) {
        for (Player player : currLobby.getPlayers().values())
            if (player.getName().equals(name))
                return false;

        return true;
    }

    private boolean validateTotem(Totem totem) {
        for (Player player : currLobby.getPlayers().values())
            if (player.getTotem().equals(totem))
                return false;

        return true;
    }

    private void handleLeave() {
        if (currLobby == null) {
            System.out.println("Invalid command, enter anything to continue: ");
            System.console().readLine();
            render();
            return;
        }
        new LeaveLobbyCommand(clientController, currLobby.getLobbyID()).execute();
    }
}
