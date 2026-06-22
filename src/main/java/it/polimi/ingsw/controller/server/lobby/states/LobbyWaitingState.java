package it.polimi.ingsw.controller.server.lobby.states;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

import java.util.Map;

public class LobbyWaitingState extends LobbyState {
    public LobbyWaitingState(LobbyController lobbyController) {
        super(lobbyController);
    }

    /** Adds a player to the lobby.
     * Once full transitions to LobbyFullState indicating that the lobby can start and no more players can join.
     * @param client the client's interface needed to communicate with the client.
     * @param player the player who requested to join containing related information.
     * */
    @Override
    public void joinLobby(ClientInterface client, Player player) {
        if (validatePlayerInfo(player) && !lobbyController.getPlayers().containsKey(client)) {
            lobbyController.getPlayers().put(client, player);

            for (ClientInterface listener : lobbyController.getListeners())
                listener.addPlayer(lobbyController.getID(), player);

            if (lobbyController.getSize() == lobbyController.getPlayers().size())
                lobbyController.setState(new LobbyFullState(lobbyController));

            ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            Logger.getInstance().print(LoggerLevel.SERVER, String.format("[Client %s] successfully joined [Lobby %s]", client.getID(), lobbyController.getID()));
        } else {
            client.showError(new ErrorMessage("Join Lobby Error", "Totem already used"));
        }
    }

    @Override
    public void startLobby(ClientInterface client) {
        client.showError(new ErrorMessage("Start Lobby Error", "Not enough players to start the game"));
    }

    /** Removes a client from the lobby's players.
     * Notifies the removal to the other remaining clients.
     * If there are no clients left, the lobby is removed.
     * @param client the client to remove.
     * */
    @Override
    public boolean removeClient(ClientInterface client) {
        Map<ClientInterface, Player> playersMap = lobbyController.getPlayers();

        ClientInterface targetKey = null;
        Player removedPlayer = null;

        for (Map.Entry<ClientInterface, Player> entry : playersMap.entrySet()) {
            if (entry.getKey().getID().equals(client.getID())) {
                targetKey = entry.getKey();
                removedPlayer = entry.getValue();
                break;
            }
        }
        if (targetKey != null) {
            playersMap.remove(targetKey);

            for (ClientInterface listener : lobbyController.getListeners()) {
                listener.removePlayer(lobbyController.getID(), removedPlayer);
            }

            if (playersMap.isEmpty()) {
                ServerController.getInstance().removeLobby(lobbyController.getID());
                ServerController.getInstance().broadcastLobbyRemoval(lobbyController.getID());
            } else {
                ServerController.getInstance().broadcastLobbyUpdate(lobbyController.getLobby());
            }
            Logger.getInstance().print(LoggerLevel.SERVER, String.format("[Client %s] successfully left [Lobby %s]", client.getID(), lobbyController.getID()));
            return true;
        }

        return false;
    }

    @Override
    public void playAction(ClientInterface client, PlayerAction action) {
        client.showError(new ErrorMessage("Lobby Action Error", "Game is not running"));
    }

    /** Checks if the player can join the lobby, meaning if the chosen totem is still available.
     * @return ture if the totem is available, false if the totem is taken by another player.
     * */
    private boolean validatePlayerInfo(Player newPlayer) {
        for (Player player : lobbyController.getPlayers().values())
            if (newPlayer.getTotem() == player.getTotem())
                return false;
        return true;
    }

    @Override
    public boolean isShowable() {
        return true;
    }
}
