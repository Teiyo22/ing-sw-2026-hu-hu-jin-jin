package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Hunter extends AbstractCharacter {
    private boolean hasIcon;

    public Hunter(int era, boolean hasIcon) {
        super(era);
        this.hasIcon = hasIcon;
    }

    @Override
    public void onPick(Player player) {

    }
}
