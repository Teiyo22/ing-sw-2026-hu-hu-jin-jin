package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class BuilderDoublePPBuilding extends AbstractBuilding implements VisitableBuilding {
    public BuilderDoublePPBuilding(int era, boolean isFinal,
                                   int cost, int pp) {
        super(era, isFinal, cost, pp);
    }

    public BuilderDoublePPBuilding(BuilderDoublePPBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new BuilderDoublePPBuilding(this);
    }

    @Override
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        if (buildingHandler !=  null)
            buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

}
