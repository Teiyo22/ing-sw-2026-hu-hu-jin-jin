package it.polimi.ingsw.controller.common.info;

import it.polimi.ingsw.controller.client.turn.EndState;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.player.Player;

public class GameEndStateInfo extends ModelStateInfo {
    public GameEndStateInfo(Player player, int idx) {
        super(player, idx);
    }

    @Override
    public TurnState getTurnState(Player player) {
        return new EndState(null, -1);
    }
}
