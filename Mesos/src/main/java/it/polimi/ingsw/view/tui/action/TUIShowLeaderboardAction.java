package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.client.turn.TurnState;

public class TUIShowLeaderboardAction implements TUIAction {
    final private ClientController clientController;

    public TUIShowLeaderboardAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "1";
    }

    @Override
    public String label() {
        return "Leaderboard";
    }

    @Override
    public boolean isEnabled() {
        Lobby currLobby = clientController.getCurrLobby();
        return currLobby != null && currLobby.getLeaderboard() == null;
    }

    @Override
    public boolean parseAction(String[] args) {
        clientController.getLeaderboard();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
