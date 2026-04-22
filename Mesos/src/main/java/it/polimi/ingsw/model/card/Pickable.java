package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public interface Pickable {
    void onPick(Player player, BuildingHandler buildingHandler);

    void removeFrom(Row row);
}
