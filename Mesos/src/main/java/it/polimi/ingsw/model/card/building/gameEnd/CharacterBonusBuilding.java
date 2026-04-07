package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class CharacterBonusBuilding extends GameEndBuilding {
    private int inventorBonusPP;
    private int shamanBonusPP;
    private int hunterBonusPP;
    private int collectorBonusPP;
    private int artistBonusPP;
    private int builderBonusPP;

    public CharacterBonusBuilding(int era, int cost, int pp, BuildingHandler buildingHandler,
                                  int inventorBonusPP,
                                  int shamanBonusPP,
                                  int hunterBonusPP,
                                  int collectorBonusPP,
                                  int artistBonusPP,
                                  int builderBonusPP) {
        super(era, cost, pp, buildingHandler);
        this.inventorBonusPP = inventorBonusPP;
        this.shamanBonusPP = shamanBonusPP;
        this.hunterBonusPP = hunterBonusPP;
        this.collectorBonusPP = collectorBonusPP;
        this.artistBonusPP = artistBonusPP;
        this.builderBonusPP = builderBonusPP;
    }

    @Override
    public void applyEffect() {
        int numInventors = owner.getTribe().getInventorCount();
        int numShamans = owner.getTribe().getShamanCount();
        int numHunters = owner.getTribe().getHunterCount();
        int numCollectors = owner.getTribe().getCollectorCount();
        int numArtists = owner.getTribe().getArtistCount();
        int numBuilders = owner.getTribe().getBuilderCount();

        owner.addPP(numInventors * inventorBonusPP + numShamans * shamanBonusPP + numHunters * hunterBonusPP + numCollectors * collectorBonusPP + numArtists * artistBonusPP + numBuilders * builderBonusPP)
    }
}
