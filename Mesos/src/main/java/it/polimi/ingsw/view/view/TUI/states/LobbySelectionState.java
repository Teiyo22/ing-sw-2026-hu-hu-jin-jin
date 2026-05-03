package it.polimi.ingsw.view.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LobbySelectionState extends ViewState {
    private enum Action {
        JOIN,
        CREATE,
        SEE_DETAILS,
        NONE
    }

    private Action action = Action.NONE;
    private int lobbyID = -1;
    private String playerName = null;
    private Totem totem = null;
    private int size = -1;
    private List<Totem> availableTotems;

    public LobbySelectionState(ClientController controller) {
        super(controller);
    }

    @Override
    public void render() {
        Map<Integer, Lobby> waitingLobbies = super.getController().getWaitingLobbies();
        Lobby currLobby = super.getController().getCurrLobby();

        System.out.println("Waiting lobbies:");
        for (Lobby lobby : waitingLobbies.values()) {
            System.out.println("> ID: " + lobby.getLobbyID() + " Size: " + lobby.getSize());
        }
        System.out.println();

        if (currLobby != null) {
            List<Player> players = new ArrayList<>(currLobby.getPlayers().values());
            String playersList = "";
            if (!players.isEmpty()) {
                playersList = players.getFirst().getName();
                for (int i = 1; i < players.size(); i++) {
                    playersList += ", " + players.get(i).getName();
                }
            }

            System.out.println("Lobby " + currLobby.getLobbyID() + ":");
            System.out.println("    size: " + currLobby.getSize());
            System.out.println("    players: " + playersList);
            System.out.println();
        }
        switch (action) {
            case NONE -> {
                System.out.println("Choose an action:");
                System.out.println("1. Join a lobby");
                System.out.println("2. Create a lobby");
                System.out.println("3. Show lobby details");
            }
            case JOIN -> {
                if (lobbyID == -1) {
                    System.out.println("Choose a lobby to join (insert ID):");
                }
                else if (playerName == null) {
                    System.out.println("Choose a name:");
                }
                else if (totem == null) {
                    System.out.println("Choose a totem:");
                    printAvailableTotems(currLobby);
                }
            }
            case CREATE -> {
                if (size == -1) {
                    System.out.println("Choose a size (1-5):");
                }
                else if (playerName == null) {
                    System.out.println("Choose a name:");
                }
                else if (totem == null) {
                    System.out.println("Choose a totem:");
                    printAvailableTotems(currLobby);
                }
            }
            case SEE_DETAILS -> {
                System.out.println("Choose a lobby to see its details (insert ID):");
            }
        }

    }


    /** Input is handled differently depending on the stage of interaction.
     * This method already checks that the inserted lobby ID is valid (ID of a lobby in waitingLobbies).*/
    @Override
    public void handleInput(String input) {
        switch (action) {
            case NONE -> {
                switch (input) {
                    case "1" -> {
                        action = Action.JOIN;
                        render();
                    }
                    case "2" -> {
                        action = Action.CREATE;
                        render();
                    }
                    case "3" -> {
                        action = Action.SEE_DETAILS;
                        render();
                    }
                }
            }
            case JOIN -> {
                if (lobbyID == -1) {
                    try {
                        if (checkLobbyID(Integer.parseInt(input))) {
                            lobbyID = Integer.parseInt(input);
                            render();
                        } else {
                            System.out.println("Invalid lobby ID. The ID is either wrong or the lobby is no longer available.");
                        }
                    }  catch (NumberFormatException e) {
                        System.out.println("Invalid input: lobby ID must be a number.");
                    }
                }
                else if (playerName == null) {
                    playerName = input;
                    render();
                }
                else if (totem == null) {
                    try {
                        int i = Integer.parseInt(input);
                        totem = availableTotems.get(i - 1);
                        Player player = new Player(playerName, totem);
                        super.getController().getServer().joinLobby(super.getController().getID(), lobbyID, player);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: select the number corresponding to the desired totem.");
                    }
                }

            }
            case CREATE -> {
                if (size == -1) {
                    try {
                        size = Integer.parseInt(input);
                        render();
                    }   catch (NumberFormatException e) {
                        System.out.println("Invalid input: lobby size must be a number.");
                    }
                }
                else if (playerName == null) {
                    playerName = input;
                    render();
                }
                else if (totem == null) {
                    try {
                        int i = Integer.parseInt(input);
                        totem = availableTotems.get(i - 1);
                        Player player = new Player(playerName, totem);
                        super.getController().getServer().createLobby(super.getController().getID(), size, player);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: select the number corresponding to the desired totem.");
                    }
                }
            }
            case SEE_DETAILS -> {
                try {
                    if (checkLobbyID(Integer.parseInt(input))) {
                        lobbyID = Integer.parseInt(input);
                        super.getController().getServer().getLobbyInfo(super.getController().getID(), lobbyID);
                    } else {
                        System.out.println("Invalid lobby ID. The ID is either wrong or the lobby is no longer available.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: lobby ID must be a number.");
                }
            }
        }

    }


    /** The method saves the available totems so that they can be used when the player chooses one by its index.
     * This guarantees that the desired totem doesn't change by computing the available totems again at a different time.
     * The server must check that the requested totem is still available and not taken by another player who sent a request at the same time.
     * */

    //TODO: add totem check on server side and manage errors.
    private void setAvailableTotems(Lobby lobby) {
        availableTotems = Arrays.stream(Totem.values()).toList();
        if (lobby != null) {
            List<Totem> takenTotems = lobby.getPlayers().values().stream().map(Player::getTotem).toList();
            availableTotems = availableTotems.stream().filter(t -> !takenTotems.contains(t)).toList();
        }
    }

    private void printAvailableTotems(Lobby lobby){
        setAvailableTotems(lobby);
        for (int i = 1; i <= availableTotems.size(); i++) {
            System.out.println(i + ". " + availableTotems.get(i-1));
        }
    }

    private boolean checkLobbyID(int id) {
        return super.getController().getWaitingLobbies().containsKey(id);
    }
}
