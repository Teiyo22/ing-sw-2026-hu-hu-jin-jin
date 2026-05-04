package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Hunter extends AbstractCharacter {
    @Expose private boolean hasIcon;

    public Hunter(int era, boolean isFinal, boolean hasIcon) {
        super(era, isFinal);
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
        p.getTribe().addHunter(hasIcon);

        if(hasIcon) {
            p.addFood(p.getTribe().getHunterCount());
        }
    }

    @Override
    public void accept(CardVisitor v){
        v.visit(this);
    }

    @Override
    public String toString() {
        return String.format("[ ID: %-3d |  %-20s  |  Era: %-3d  |  Icon: %b ]", getID(), super.getClass().getSimpleName(), super.getEra(), hasIcon);
    }
}
