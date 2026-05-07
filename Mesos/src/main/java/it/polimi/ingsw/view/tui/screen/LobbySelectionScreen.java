package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.command.*;
import it.polimi.ingsw.view.tui.Formatter;

import java.util.Map;


public class LobbySelectionScreen implements Screen {
    private final ClientController clientController;
    private Map<Integer, Lobby> waitingLobbies;
    private Lobby currLobby;

    private String errorMsg = "";
    private String nextInputMsg = "Enter action: ";

    public LobbySelectionScreen(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void render() {
        Formatter.clearScreen();
        printAvailableActions();
        printLobbyInfo();
        printLobbyList();

        System.out.println(Formatter.formatSeparatorLine(""));
        System.out.println("\u001B[1m\u001B[31m" + errorMsg + "\u001B[0m");
        System.out.print(nextInputMsg);
    }

    @Override
    public void handleInput(String input) {
        switch (input.toLowerCase()) {
            case "0", "disconnect" -> new DisconnectCommand(clientController).execute();
            case "1", "list" -> new GetWaitingLobbiesCommand(clientController).execute();
            case "2", "create" -> handleCreate();
            case "3", "info" -> handleInfo();
            case "4", "join" -> handleJoin();
            case "5", "leave" -> handleLeave();
            case "6", "start" -> handleStart();
            default -> handleInvalidInput();
        }
    }

    @Override
    public void update() {
        waitingLobbies = clientController.getWaitingLobbies();
        currLobby = clientController.getCurrLobby();
    }

    @Override
    public void onExit() {

    }

    private void printAvailableActions() {
        System.out.println(Formatter.formatSeparatorLine("Lobby Selection"));
        System.out.println(Formatter.formatLine("Available actions:"));
        System.out.println(Formatter.formatLine("0. Disconnect"));
        System.out.println(Formatter.formatLine("1. List"));

        if (currLobby == null || !currLobby.containsClient(clientController.getID()))
            System.out.println(Formatter.formatLine("2. Create"));

        if (waitingLobbies != null && !waitingLobbies.isEmpty() && !(currLobby != null && currLobby.containsClient(clientController.getID())))
            System.out.println(Formatter.formatLine("3. Info"));

        if (currLobby != null) {
            if (!currLobby.containsClient(clientController.getID()))
                System.out.println(Formatter.formatLine("4. Join"));
            else
                System.out.println(Formatter.formatLine("5. Leave"));
        }

        if (currLobby != null && currLobby.containsClient(clientController.getID()) && currLobby.getPlayerCount() == currLobby.getSize())
            System.out.println(Formatter.formatLine("6. Start"));
    }

    private void printLobbyList() {
        if (waitingLobbies == null || waitingLobbies.isEmpty())
            return;

        System.out.println(Formatter.formatSeparatorLine("Lobby List"));
        for (Lobby lobby : waitingLobbies.values())
            System.out.println(Formatter.formatLine(String.format("Lobby ID: %3d | Size: %3d", lobby.getLobbyID(), lobby.getSize())));
    }

    private void printLobbyInfo() {
        if (currLobby == null)
            return;

        System.out.println(Formatter.formatSeparatorLine("Lobby Info"));
        System.out.println(Formatter.formatLine(String.format("Lobby ID:     %3d", currLobby.getLobbyID())));
        System.out.println(Formatter.formatLine(String.format("Player Count: %3d/%3d", currLobby.getPlayerCount(), currLobby.getSize())));
        System.out.println(Formatter.formatLine("Players:"));
        for (Player player : currLobby.getPlayers().keySet())
            System.out.println(Formatter.formatColoredLine(String.format(" - %s", player.getName()), player.getTotem().getColor()));
    }

    private void handleCreate() {
        int lobbySize;
        String playerName;
        Totem totem = null;

        if (currLobby != null && currLobby.containsClient(clientController.getID())) {
            handleInvalidInput();
            return;
        }

        resetMsg();
        nextInputMsg = "Enter lobby size (2-5) (Enter 'q' to cancel): ";
        do {
            render();

            String input = System.console().readLine().trim();

            if (input.equalsIgnoreCase("q")) {
                resetMsg();
                render();
                return;
            }

            try {
                lobbySize = Integer.parseInt(input);

                if (lobbySize < 2 || lobbySize > 5)
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                lobbySize = -1;
                errorMsg = "Invalid input: lobby size must be a number between 2 and 5.";
            }
        } while (lobbySize == -1);

        resetMsg();
        nextInputMsg = "Enter player name (Enter 'q' to cancel): ";
        render();
        playerName = System.console().readLine().trim();

        if (playerName.equalsIgnoreCase("q")) {
            resetMsg();
            render();
            return;
        }

        resetMsg();
        nextInputMsg = "Choose a totem (RED|BLUE|WHITE|BLACK|YELLOW) (Enter 'q' to cancel): ";
        do {
            render();

            String input = System.console().readLine().trim();

            if (input.equalsIgnoreCase("q")) {
                resetMsg();
                render();
                return;
            }

            try {
                totem = Totem.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                errorMsg = "Invalid input: totem must be one of RED, BLUE, WHITE, BLACK, YELLOW.";
            }
        } while (totem == null);

        new CreateLobbyCommand(clientController, lobbySize, new Player(playerName, totem)).execute();

        resetMsg();
        render();
    }

    private void handleInfo() {
        int lobbyID;

        if (waitingLobbies == null || waitingLobbies.isEmpty() || (currLobby != null && currLobby.containsClient(clientController.getID()))) {
            handleInvalidInput();
            return;
        }

        resetMsg();
        nextInputMsg = "Enter lobby ID (Enter 'q' to cancel): ";
        do {
            render();

            String input = System.console().readLine().trim();

            if (input.equalsIgnoreCase("q")) {
                resetMsg();
                render();
                return;
            }

            try {
                lobbyID = Integer.parseInt(input);

                if (!waitingLobbies.containsKey(lobbyID)) {
                    errorMsg = "Invalid lobby ID.";
                    lobbyID = -1;
                }
            } catch (NumberFormatException e) {
                errorMsg = "Invalid input: lobby size must be a number.";
                lobbyID = -1;
            }

        } while (lobbyID == -1);

        new LobbyInfoCommand(clientController, lobbyID).execute();

        resetMsg();
        render();
    }

    private void handleJoin() {
        String playerName;
        Totem totem;

        if (currLobby == null || currLobby.containsClient(clientController.getID())) {
            handleInvalidInput();
            return;
        }

        resetMsg();
        nextInputMsg = "Enter player name (Enter 'q' to cancel): ";
        do {
            render();

            playerName = System.console().readLine().trim();

            if (playerName.equalsIgnoreCase("q")) {
                resetMsg();
                render();
                return;
            }

            if (!validateName(playerName)) {
                errorMsg = "Invalid player name, choose another one.";
                playerName = null;
            }
        } while (playerName == null);

        resetMsg();
        nextInputMsg = "Choose a totem (RED|BLUE|WHITE|BLACK|YELLOW) (Enter 'q' to cancel): ";
        do {
            render();

            String input = System.console().readLine().trim();

            if (input.equalsIgnoreCase("q")) {
                resetMsg();
                render();
                return;
            }

            try {
                totem = Totem.valueOf(input.toUpperCase());

                if (!validateTotem(totem)) {
                    errorMsg = "Invalid totem, choose another one.";
                    totem = null;
                }

            } catch (IllegalArgumentException e) {
                errorMsg = "Invalid input: totem must be one of RED, BLUE, WHITE, BLACK, YELLOW.";
                totem = null;
            }
        } while (totem == null);

        new JoinLobbyCommand(clientController, currLobby.getLobbyID(), new Player(playerName, totem)).execute();

        resetMsg();
        render();
    }

    private void handleLeave() {
        if (currLobby == null) {
            handleInvalidInput();
            return;
        }
        new LeaveLobbyCommand(clientController, currLobby.getLobbyID()).execute();

        resetMsg();
        render();
    }

    private void handleStart() {
        if (currLobby == null ||
            !currLobby.containsClient(clientController.getID()) ||
            currLobby.getPlayerCount() != currLobby.getSize()) {
            handleInvalidInput();
            return;
        }

        new StartLobbyCommand(clientController, currLobby.getLobbyID()).execute();

        resetMsg();
        render();
    }

    private boolean validateName(String name) {
        for (Player player : currLobby.getPlayers().keySet())
            if (player.getName().equals(name))
                return false;

        return true;
    }

    private boolean validateTotem(Totem totem) {
        for (Player player : currLobby.getPlayers().keySet())
            if (player.getTotem().equals(totem))
                return false;

        return true;
    }

    private void handleInvalidInput() {
        errorMsg = "Invalid command.";
        render();
    }

    private void resetMsg() {
        errorMsg = "";
        nextInputMsg = "Enter action: ";
    }
}
