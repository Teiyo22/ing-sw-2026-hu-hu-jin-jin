package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Inventor extends AbstractCharacter{
    private InventorType inventorType;

    public Inventor(String type, int era, boolean isFinal, InventorType inventorType) {
        super(type, era, isFinal);
        this.inventorType = inventorType;
    }

    @Override
    public void onPick(Player player) {

    }
}
