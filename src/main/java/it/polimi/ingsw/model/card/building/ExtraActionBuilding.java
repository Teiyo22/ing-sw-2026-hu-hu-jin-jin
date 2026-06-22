package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionBuilding extends AbstractBuilding implements VisitableBuilding {
    public ExtraActionBuilding(int era, boolean isFinal, int cost, int pp) {
        super(era, isFinal, cost, pp);
    }

    public ExtraActionBuilding(ExtraActionBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new ExtraActionBuilding(this);
    }

    /**
     * Adds the card to the list of extra actions buildings in the building handler.
     * */
    @Override
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        if (buildingHandler !=  null)
            buildingHandler.addExtraActionBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

}
