package it.polimi.ingsw.controller.common.info;

import it.polimi.ingsw.controller.client.turn.IdleState;
import it.polimi.ingsw.controller.client.turn.OfferPickState;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.player.Player;

public class OfferPickStateInfo extends ModelStateInfo {
    public OfferPickStateInfo(Player player, int idx) {
        super(player, idx);
        type = StateInfoType.OFFER_PICk;
    }

    @Override
    public TurnState getTurnState(Player player) {
        return currPlayer.equals(player) ? new OfferPickState(player, idx) : new IdleState(player, idx);
    }
}
