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

    /**
     * Every shaman has stars so when we pick one shaman we add the number of stars*/
    @Override
    public void onPick(Player player) {
        player.getTribe().addShaman();
        player.getTribe().addStars(getStar);
    }

    public int getStar(){
        return this.stars;
    }
}
