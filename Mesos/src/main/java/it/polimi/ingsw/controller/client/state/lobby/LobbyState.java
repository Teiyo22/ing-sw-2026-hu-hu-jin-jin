package it.polimi.ingsw.controller.client.state.lobby;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.state.ClientState;

public abstract class LobbyState extends ClientState {
    public LobbyState(ClientController clientController) {
        super(clientController);
    }
}
