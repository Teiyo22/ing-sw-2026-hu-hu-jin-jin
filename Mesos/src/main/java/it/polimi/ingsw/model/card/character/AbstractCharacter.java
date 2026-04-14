package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.Visitable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

public abstract class AbstractCharacter extends AbstractCard implements Pickable, Visitable {
    public AbstractCharacter(String type, int era, boolean isFinal) {
        super(type, era, isFinal);
    }

    public AbstractCharacter(AbstractCharacter source) {
        super(source);
    }

    public abstract void addToTribeOf(Player p);

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        addToTribeOf(player);
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


}
