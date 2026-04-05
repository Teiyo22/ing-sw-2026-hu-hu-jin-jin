package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Shaman extends AbstractCharacter{
    private int stars;

    public Shaman(int era, int stars) {
        super(era);
        this.stars = stars;
    }

    @Override
    public String getCharacterTyper(){
        return "Shaman";
    }

    @Override
    public void onPick(Player player) {
        player.getTribe().addShaman();
    }

    public int getStar(){
        return this.stars;
    }
}
