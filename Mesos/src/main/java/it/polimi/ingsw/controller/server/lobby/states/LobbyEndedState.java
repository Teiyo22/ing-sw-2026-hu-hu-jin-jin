package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;

public class LobbyEndedState extends LobbyState {
    public LobbyEndedState(LobbyController lobbyController) {
        super(lobbyController);
        lobbyController.setModel(null);
        ServerController.getInstance().removeFromPlayingClients(lobbyController.getPlayers().keySet());
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(new ErrorMessage("Join Lobby Error", "The game already ended."));
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(new ErrorMessage("Start Lobby Error", "The game already ended."));
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            client.removeLobby(lobbyController.getID());

            if (lobbyController.getPlayers().isEmpty())
                ServerController.getInstance().removeLobby(lobbyController.getID());

            return true;
        }

        return false;
    }

    @Override
    public void getLobbyInfo(ClientInterface client) {
        client.showError(new ErrorMessage("Lobby Info Error", "The game already ended."));
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError(new ErrorMessage("Lobby Action Error", "The game already ended."));
    }

    @Override
    public boolean isShowable() {
        return false;
    }
}
