package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Builder extends AbstractCharacter{
    @Expose private int bonusPP;
    @Expose private int buildingDiscount;

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
        v.visit(this);
    }


    @Override
    public String toString() {
        return String.format("[ ID: %-3d |  %-20s  |  Era: %-3d  |  Bonus PP: %-3d  |  Building Discount: %-3d ]",
                                getID(), super.getClass().getSimpleName(), super.getEra(), bonusPP, buildingDiscount);
    }
}
