package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class Collector extends AbstractCharacter{
    public Collector(String type, int era, boolean isFinal) {
        super(type, era, isFinal);
    }

    public Collector(Collector source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new Collector(this);
    }

    /**
     * Every collector gives a discount, so we add the discount on sustenanceDiscount */
    @Override
    public void onPick(Player player) {
        player.getTribe().addCollector();
        player.getTribe().addSustenanceDiscount(3);
    }

    @Override
    public void accept(CardVisitor v){
        v.doForCollector(this);
    }
}
