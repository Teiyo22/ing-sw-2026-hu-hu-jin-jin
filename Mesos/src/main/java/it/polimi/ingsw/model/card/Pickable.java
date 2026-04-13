package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public interface Pickable {
    public abstract void onPick(Player player, BuildingHandler buildingHandler);

    public abstract void remove(Row row, int removedIndex);
}
