package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public abstract class AbstractCharacter extends AbstractCard implements Pickable{
    public AbstractCharacter(int era, boolean isFinal) {
        super(era, isFinal);
    }

    public AbstractCharacter(AbstractCharacter source) {
        super(source);
    }

    public abstract void addToTribeOf(Player p);

    /**
     * Adds the character to the player's tribe. Different methods are called depending on the character type.
     * Also applies the effects of card pick buildings.
     *
     * @param player is the player who picked the card.
     * @param buildingHandler is used to apply the effects of card pick buildings.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        addToTribeOf(player);
        if (buildingHandler != null)
            buildingHandler.applyCardPickEffects(this, player);
    }

    @Override
    public void moveTo(Row row) {
        row.addCharacter(this);
    }

    @Override
    public void removeFrom(Row row){
        row.getCharacterCards().remove(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
