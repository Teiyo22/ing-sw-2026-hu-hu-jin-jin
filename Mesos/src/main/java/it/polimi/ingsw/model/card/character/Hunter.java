package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Hunter extends AbstractCharacter {
    private final boolean hasIcon;

    public Hunter(String type, int era, boolean isFinal, boolean hasIcon) {
        super(type, era, isFinal);
        this.hasIcon = hasIcon;
    }

    public Hunter(Hunter source) {
        super(source);
        this.hasIcon = source.hasIcon;
    }

    @Override
    public AbstractCard clone() {
        return new Hunter(this);
    }

    /**The hunter gives immediately food if he has the icon
     * so when we pick the card we check if it has the icon */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        player.getTribe().addHunter();
        if(hasIcon){
            player.addFood(player.getTribe().getHunterCount());
        }
    }

    @Override
    public void accept(CardVisitor v){
        v.doForHunter(this);
    }
}
