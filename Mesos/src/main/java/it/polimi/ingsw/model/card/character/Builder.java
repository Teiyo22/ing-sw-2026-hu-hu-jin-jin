package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

public class Builder extends AbstractCharacter{
    private int bonusPP;
    private int buildingDiscount;

    public Builder(String type, int era, boolean isFinal, int bonusPP, int buildingDiscount) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.buildingDiscount = buildingDiscount;
    }

    public Builder(Builder source) {
        super(source);
        this.buildingDiscount = source.buildingDiscount;
        this.bonusPP = source.bonusPP;
    }

    @Override
    public AbstractCard clone() {
        return new Builder(this);
    }

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addBuilder(this);
    }

    public int getBonusPP() {
        return bonusPP;
    }

    public int getBuildingDiscount() {
        return buildingDiscount;
    }

    @Override
    public void accept(CardVisitor v){
        v.doForBuilder(this);
    }
}
