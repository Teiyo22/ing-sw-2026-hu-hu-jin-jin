package it.polimi.ingsw.controller.common.info;

import it.polimi.ingsw.controller.client.turn.CardPickState;
import it.polimi.ingsw.controller.client.turn.IdleState;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.player.Player;

public class CardPickStateInfo extends ModelStateInfo {

    public CardPickStateInfo(Player player, int idx) {
        super(player, idx);
        type = StateInfoType.CARD_PICK;
    }

    @Override
    public TurnState getTurnState(Player player) {
        return currPlayer.equals(player) ? new CardPickState(currPlayer, idx) : new IdleState(currPlayer, idx);
    }
}
