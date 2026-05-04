package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class BuilderBonusBuilding extends AbstractBuilding implements VisitableBuilding {
    public BuilderBonusBuilding(int era, boolean isFinal,
                                int cost, int pp) {
        super(era, isFinal, cost, pp);
    }

    public BuilderBonusBuilding(BuilderBonusBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new BuilderBonusBuilding(this);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return String.format("[ Type: %s  |  Era: %d  |  Cost: %d  |  PP: %d ]",
                super.getClass(), super.getEra(), super.getCost(), super.getPP());
    }
}
