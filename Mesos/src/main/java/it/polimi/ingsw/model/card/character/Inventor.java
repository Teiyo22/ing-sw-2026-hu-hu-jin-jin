package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Inventor extends AbstractCharacter{
    private InventorType type;

    public Inventor(int era, InventorType type) {
        super(era);
        this.type = type;
    }

    @Override
    public void onPick(Player player) {

    }
}
