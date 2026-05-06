package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
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

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addCollector();
        p.getTribe().addSustenanceDiscount(3);
    }

    @Override
    public void accept(CardVisitor v){
        v.visit(this);
    }

    @Override
    public String toString() {
        return String.format("[ ID: %-3d |  %-20s  |  Era: %-3d ]", getID(), super.getClass().getSimpleName(), super.getEra());
    }
}
