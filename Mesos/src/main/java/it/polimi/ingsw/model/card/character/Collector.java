package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Collector extends AbstractCharacter{
    public Collector(int era) {
        super(era);
    }

    @Override
    public String getCharacterTyper(){
        return "Collector";
    }

    @Override
    public void onPick(Player player) {
        player.getTribe().addCollector();
    }
}
