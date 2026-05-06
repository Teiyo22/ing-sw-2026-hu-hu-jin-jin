package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class FullSetBuilding extends AbstractBuilding implements VisitableBuilding {
    public FullSetBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public FullSetBuilding(FullSetBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new FullSetBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public String toString() {
        return String.format("[ ID: %-3d |  %-20s  |  Era: %-3d  |  Cost: %-3d  |  PP: %-3d ]",
                getID(), super.getClass().getSimpleName(), super.getEra(), super.getCost(), super.getPP());
    }
}
