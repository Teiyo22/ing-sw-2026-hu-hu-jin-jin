package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Artist extends AbstractCharacter {
    public Artist(String type, int era, boolean isFinal) {
        super(type, era, isFinal);
    }

    public Artist(int era) {
        super(era);
    }

    @Override
    public void onPick(Player player) {

    }
}
