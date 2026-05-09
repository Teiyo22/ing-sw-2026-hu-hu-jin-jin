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
        String format = " %-10s | %-26s | %-15s ";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());

        return String.format(format, ID, type, ERA);
    }
}
