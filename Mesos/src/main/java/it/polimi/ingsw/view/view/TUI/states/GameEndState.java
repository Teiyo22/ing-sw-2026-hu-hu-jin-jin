package it.polimi.ingsw.view.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class GameEndState extends ViewState {
    public GameEndState(ClientController controller) {
        super(controller);
    }

    @Override
    public void render() {
        Map<Integer, Integer> rankings = getController().getRankings();
        Map<Integer, Player> players = getController().getCurrLobby().getPlayers();

        System.out.println("Game ended. Final rankings: ");

        rankings.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                .forEach(e -> {
                    Player player = players.get(e.getValue());
                    System.out.println(e.getKey() + ". " + player.getName() + "  |  PP: " + player.getPP() + "  |  Food: " +  player.getFood());
                });

        System.out.println("\n[s] Start or join another game.");
    }

    @Override
    public void handleInput(String input) {
        if (input.equals("s")) {
            getController().getServer().getWaitingLobbies(getController().getID());
        } else {
            System.out.println("Invalid input.");
        }
    }
}
