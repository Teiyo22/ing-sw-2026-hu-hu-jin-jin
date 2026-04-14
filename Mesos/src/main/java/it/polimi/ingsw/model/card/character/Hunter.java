package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

public class Hunter extends AbstractCharacter {
    @Expose private final boolean hasIcon;

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

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addHunter();
        if(hasIcon) {
            p.addFood(p.getTribe().getHunterCount());
        }
    }

    @Override
    public void accept(CardVisitor v){
        v.doForHunter(this);
    }
}
