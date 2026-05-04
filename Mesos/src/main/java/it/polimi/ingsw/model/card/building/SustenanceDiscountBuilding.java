package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class SustenanceDiscountBuilding extends AbstractBuilding implements VisitableBuilding, CardVisitor {
    @Expose int inventorDiscount;
    @Expose int shamanDiscount;
    @Expose int hunterDiscount;
    @Expose int collectorDiscount;
    @Expose int artistDiscount;
    @Expose int builderDiscount;

    public SustenanceDiscountBuilding(int era, boolean isFinal, int cost, int pp,
                                      int inventorDiscount, int shamanDiscount, int hunterDiscount,
                                      int artistDiscount, int collectorDiscount, int builderDiscount) {
        super(era, isFinal, cost, pp);
        this.inventorDiscount = inventorDiscount;
        this.shamanDiscount = shamanDiscount;
        this.hunterDiscount = hunterDiscount;
        this.collectorDiscount = collectorDiscount;
        this.artistDiscount = artistDiscount;
        this.builderDiscount = builderDiscount;
    }

    public SustenanceDiscountBuilding(SustenanceDiscountBuilding source) {
        super(source);
        this.inventorDiscount = source.inventorDiscount;
        this.shamanDiscount = source.shamanDiscount;
        this.hunterDiscount = source.hunterDiscount;
        this.collectorDiscount = source.collectorDiscount;
        this.artistDiscount = source.artistDiscount;
        this.builderDiscount = source.builderDiscount;
    }

    @Override
    public AbstractCard clone() {
        return new SustenanceDiscountBuilding(this);
    }

    /**
     * If the picked card is an inventor card, the discount associated to the inventor is added to the tribe.
     * */
    @Override
    public void visit(Inventor i) {
        owner.getTribe().addSustenanceDiscount(inventorDiscount);
    }

    @Override
    public void visit(Shaman s) {
        owner.getTribe().addSustenanceDiscount(shamanDiscount);
    }

    @Override
    public void visit(Hunter h) {
        owner.getTribe().addSustenanceDiscount(hunterDiscount);
    }

    @Override
    public void visit(Collector c) {
        owner.getTribe().addSustenanceDiscount(collectorDiscount);
    }

    @Override
    public void visit(Artist a) {
        owner.getTribe().addSustenanceDiscount(artistDiscount);
    }

    @Override
    public void visit(Builder b) {
        owner.getTribe().addSustenanceDiscount(builderDiscount);
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
}
