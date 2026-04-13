package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Shaman extends AbstractCharacter{
    private int stars;

    public Shaman(String type, int era, boolean isFinal, int stars) {
        super(type, era, isFinal);
        this.stars = stars;
    }

    @Override
    public void onPick(Player player) {

    }
}
