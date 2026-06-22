package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.Set;


public class OfferResolutionResponse extends Response {
    private int lobbyID;
    private Player player;
    private Set<Integer> topRowPicks;
    private Set<Integer> bottomRowPicks;

    public OfferResolutionResponse(int lobbyID, Player player, Set<Integer> getTopRowPicks, Set<Integer> getBottomRowPicks ){
        this.lobbyID = lobbyID;
        this.player = player;
        this.topRowPicks = getTopRowPicks;
        this.bottomRowPicks = getBottomRowPicks;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, player, topRowPicks, bottomRowPicks);
    }
}

