package it.polimi.ingsw.controller.client.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class LobbyWaitingState extends ViewState {
    private boolean full;

    public LobbyWaitingState(ClientController controller) {
        super(controller);
    }

    @Override
    public void render() {
        Lobby lobby = super.getController().getCurrLobby();

        full = lobby.getPlayers().size() == lobby.getSize();

        List<String> playerNames = lobby.getPlayers().values().stream().map(Player::getName).toList();
        String players = String.join(", ", playerNames);
        System.out.println("Lobby " + lobby.getLobbyID());
        System.out.println("    Players: " + players);

        if (full) {
            System.out.println("[s] Start the game.");
        } else {
            System.out.println("Waiting for all the players to join the lobby.");
        }

        //TODO: manage case where multiple players try to start the lobby at the same time.
        //      Either make the command available only for one player or manage errors on server's side.
    }

    @Override
    public void handleInput(String input) {
        if (full) {
            if (input.equals("s")) {
                super.getController().getServer().startLobby(super.getController().getID(),
                        super.getController().getCurrLobby().getLobbyID());
            } else {
                System.out.println("Invalid input.");
            }
        }
    }
}
