package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;

public class LobbyResumableState extends LobbyState {
    public LobbyResumableState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(new ErrorMessage("Join Lobby Error", "You are already in the lobby"));
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removeClient(lobbyController.getID(), new Player(removedPlayer.getName(), removedPlayer.getTotem()));

            lobbyController.setState(new LobbyPausedState(lobbyController));
            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            return true;
        }

        return false;
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError(new ErrorMessage("Lobby Action Error", "Game not started yet"));
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
