package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Artist extends AbstractCharacter {
    public Artist(int era) {
        super(era);
    }

    @Override
    public String getCharacterType() {
        return "Artist";
    }

    @Override
    public void onPick(Player player) {
        player.getTribe().addArtist();
    }
}
