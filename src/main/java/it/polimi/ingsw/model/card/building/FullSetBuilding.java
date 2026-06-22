package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class FullSetBuilding extends AbstractBuilding implements VisitableBuilding {
    public FullSetBuilding(int era, boolean isFinal, int cost, int pp) {
        super(era, isFinal, cost, pp);
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
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        if (buildingHandler !=  null)
            buildingHandler.addGameEndBuilding(this);
    }
}
