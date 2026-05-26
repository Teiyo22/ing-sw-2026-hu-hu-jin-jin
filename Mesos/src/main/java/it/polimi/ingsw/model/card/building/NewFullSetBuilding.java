package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class NewFullSetBuilding extends AbstractBuilding implements VisitableBuilding {
    transient int minForSet;

    public NewFullSetBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public NewFullSetBuilding(NewFullSetBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new NewFullSetBuilding(this);
    }


    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        if (buildingHandler !=  null)
            buildingHandler.addCardPickBuilding(this);
        minForSet = owner.getTribe().getMinChar() + 1;
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    public int getMinForSet() {
        return minForSet;
    }

    public void setMinForSet(int minForSet) {
        this.minForSet = minForSet;
    }
}
