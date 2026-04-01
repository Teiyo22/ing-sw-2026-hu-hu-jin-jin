package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Shaman extends AbstractCharacter{
    private int stars;

    public Shaman(int era, int stars) {
        super(era);
        this.stars = stars;
    }

    @Override
    public void onPick(Player player) {

    }
}
