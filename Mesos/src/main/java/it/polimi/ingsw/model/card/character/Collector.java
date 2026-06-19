package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Collector extends AbstractCharacter{
    public Collector(int era, boolean isFinal) {
        super(era, isFinal);
    }

    public Collector(Collector source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new Collector(this);
    }

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addCollector(this);
        p.getTribe().addSustenanceDiscount(3);
    }

    @Override
    public void accept(CardVisitor v){
        v.visit(this);
    }

}
