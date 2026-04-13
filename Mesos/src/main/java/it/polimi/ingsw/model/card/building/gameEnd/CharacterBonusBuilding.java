package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class CharacterBonusBuilding extends GameEndBuilding {
    private int inventorBonusPP;
    private int shamanBonusPP;
    private int hunterBonusPP;
    private int collectorBonusPP;
    private int artistBonusPP;
    private int builderBonusPP;

    public CharacterBonusBuilding(String type, int era, boolean isFinal,
                                  int cost, int pp, BuildingHandler buildingHandler,
                                  int inventorBonusPP, int shamanBonusPP, int hunterBonusPP,
                                  int collectorBonusPP, int artistBonusPP, int builderBonusPP) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.inventorBonusPP = inventorBonusPP;
        this.shamanBonusPP = shamanBonusPP;
        this.hunterBonusPP = hunterBonusPP;
        this.collectorBonusPP = collectorBonusPP;
        this.artistBonusPP = artistBonusPP;
        this.builderBonusPP = builderBonusPP;
    }

    public CharacterBonusBuilding(CharacterBonusBuilding source) {
        super(source);
        this.inventorBonusPP = source.inventorBonusPP;
        this.shamanBonusPP = source.shamanBonusPP;
        this.hunterBonusPP = source.hunterBonusPP;
        this.collectorBonusPP = source.collectorBonusPP;
        this.artistBonusPP = source.artistBonusPP;
        this.builderBonusPP = source.builderBonusPP;
    }

    @Override
    public AbstractCard clone() {
        return new CharacterBonusBuilding(this);
    }

    @Override
    public void applyEffect() {

    }
}
