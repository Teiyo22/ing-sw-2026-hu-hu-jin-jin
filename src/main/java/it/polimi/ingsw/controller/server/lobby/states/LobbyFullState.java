package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

public class LobbyFullState extends LobbyState {
    public LobbyFullState(LobbyController lobbyController) {
        super(lobbyController);
    }

    @Override
    public void joinLobby(ClientInterface client, Player player) {
        client.showError(new ErrorMessage("Join Lobby Error", "The lobby is full"));
    }

    @Override
    public boolean removeClient(ClientInterface client) {
        Player removedPlayer = lobbyController.getPlayers().remove(client);

        if (removedPlayer != null) {
            for (ClientInterface listener : lobbyController.getListeners())
                listener.removePlayer(lobbyController.getID(), removedPlayer);

            lobbyController.setState(new LobbyWaitingState(lobbyController));
            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            Logger.getInstance().print(LoggerLevel.SERVER, String.format("[Client %s] successfully left [Lobby %s]", client.getID(), lobbyController.getID()));
            return true;
        }

        return false;
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError(new ErrorMessage("Lobby Action Error", "The lobby is full"));
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
