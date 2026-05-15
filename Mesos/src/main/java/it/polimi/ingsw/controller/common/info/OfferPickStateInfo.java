package it.polimi.ingsw.controller.common.info;

import it.polimi.ingsw.controller.client.turn.IdleState;
import it.polimi.ingsw.controller.client.turn.OfferPickState;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.player.Player;

public class OfferPickStateInfo extends ModelStateInfo {
    public OfferPickStateInfo(Player player, int idx, int era) {
        super(player, idx, era);
        type = StateInfoType.OFFER_PICK;
    }

    @Override
    public TurnState getTurnState(Player player) {
        return currPlayer.equals(player) ? new OfferPickState(currPlayer, idx, era) : new IdleState(currPlayer, idx, era);
    }
}
