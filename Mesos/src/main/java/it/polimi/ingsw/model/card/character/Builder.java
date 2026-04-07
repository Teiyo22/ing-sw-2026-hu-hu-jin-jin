package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Builder extends AbstractCharacter{
    private int bonusPP;
    private int buildingDiscount;

    public Builder(int era, int bonusPP, int buildingDiscount) {
        super(era);
        this.bonusPP = bonusPP;
        this.buildingDiscount = buildingDiscount;
    }
    @Override
    public String getCharacterType() {
        return "Builder";
    }
    @Override
    public void onPick(Player player) {
        player.getTribe().addBuilder();
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
