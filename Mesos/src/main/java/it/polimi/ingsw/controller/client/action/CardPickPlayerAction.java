package it.polimi.ingsw.controller.client.action;

import it.polimi.ingsw.model.Game;

import java.util.Set;

public class CardPickPlayerAction extends PlayerAction {
    private Set<Integer> topPicks;
    private Set<Integer> bottomPicks;

    public CardPickPlayerAction(Set<Integer> topPicks, Set<Integer> bottomPicks) {
        this.type = ActionType.CARD_PICK;
        this.topPicks = topPicks;
        this.bottomPicks = bottomPicks;
    }

    @Override
    public String[] canExecute(Game game) {
        return game.getGameState().validate(this);
    }

    @Override
    public void execute(Game game) {
        game.pick(player, topPicks, bottomPicks);
    }

    public Set<Integer> getTopPicks() {
        return topPicks;
    }

    public Set<Integer> getBottomPicks() {
        return bottomPicks;
    }
}
