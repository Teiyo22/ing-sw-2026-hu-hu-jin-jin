package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

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
    public void onPick(Player player) {

    }
}
