package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Inventor extends AbstractCharacter{
    @Expose private InventorType inventorType;

    public Inventor(String type, int era, boolean isFinal, InventorType inventorType) {
        super(type, era, isFinal);
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
        v.doForInventor(this);
    }

    public InventorType getInventorType() {
        return this.inventorType;
    }

}