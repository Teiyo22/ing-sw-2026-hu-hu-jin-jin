package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Collector extends AbstractCharacter{
    public Collector(String type, int era, boolean isFinal) {
        super(type, era, isFinal);
    }

    @Override
    public void onPick(Player player) {

    }
}
