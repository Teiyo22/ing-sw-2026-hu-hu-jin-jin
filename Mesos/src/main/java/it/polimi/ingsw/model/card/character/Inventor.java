package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Inventor extends AbstractCharacter{
    @Expose private InventorType inventorType;

    public Inventor(int era, boolean isFinal, InventorType inventorType) {
        super(era, isFinal);
        this.inventorType = inventorType;
    }

    public Inventor(Inventor source) {
        super(source);
        this.inventorType = source.inventorType;
    }

    @Override
    public AbstractCard clone() {
        return new Inventor(this);
    }

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addInventor(this);
    }

    @Override
    public void accept(CardVisitor v) {
        v.visit(this);
    }

    public InventorType getInventorType() {
        return this.inventorType;
    }

    @Override
    public String toString() {
        return String.format("[ ID: %3d |  %20s, %20s  |  Era: %3d ]", getID(), super.getClass().getSimpleName(), inventorType, super.getEra());
    }

}