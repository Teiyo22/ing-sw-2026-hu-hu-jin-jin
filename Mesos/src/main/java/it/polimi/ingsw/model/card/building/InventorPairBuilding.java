package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class InventorPairBuilding extends AbstractBuilding implements VisitableBuilding {
    public InventorPairBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public InventorPairBuilding(InventorPairBuilding source) {
        super(source);
    }
    
    @Override
    public AbstractCard clone() {
        return new InventorPairBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addCardPickBuilding(this);
    }

    @Override
    public String toString() {
        return String.format("[ Type: %s  |  Era: %d  |  Cost: %d  |  PP: %d ]",
                super.getType(), super.getEra(), super.getCost(), super.getPP());
    }
}
