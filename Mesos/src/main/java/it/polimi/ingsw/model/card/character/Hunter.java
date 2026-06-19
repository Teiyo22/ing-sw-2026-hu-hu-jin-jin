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
        if(hasIcon) 
            p.addFood(p.getTribe().getHunterCount());

        p.getTribe().addHunter(this);
    }

    @Override
    public void accept(CardVisitor v){
        v.visit(this);
    }

    @Override
    public String toString() {
        String format = "| %-15s ";
        String ICON = String.format("Icon: %b", hasIcon);

        return super.toString() + String.format(format, ICON);
    }
}
