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

    public SustenanceDiscountBuilding(String type, int era, boolean isFinal, int cost, int pp,
                                      int inventorDiscount, int shamanDiscount, int hunterDiscount,
                                      int artistDiscount, int collectorDiscount, int builderDiscount) {
        super(type, era, isFinal, cost, pp);
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

    @Override
    public String toString() {
        String discountString = "";

        discountString = inventorDiscount == 0 ? discountString :
                discountString + String.format("| Discount (Inventor): %-3d", inventorDiscount);
        discountString = shamanDiscount == 0 ? discountString :
                discountString + String.format("| Discount (Shaman): %-3d", shamanDiscount);
        discountString = hunterDiscount == 0 ? discountString :
                discountString + String.format("| Discount (Hunter): %-3d ", hunterDiscount);
        discountString = collectorDiscount == 0 ? discountString :
                discountString + String.format("| Discount (Collector): %-3d", collectorDiscount);
        discountString = artistDiscount == 0 ? discountString :
                discountString + String.format("| Discount (Artist): %-3d", artistDiscount);
        discountString = builderDiscount == 0 ? discountString :
                discountString + String.format("| Discount (Builder): %-3d", builderDiscount);

        return String.format("[ ID: %-3d |  %-20s  |  Era: %-3d  |  Cost: %-3d  |  PP: %-3d  %s ]",
                getID(), super.getClass().getSimpleName(), super.getEra(), super.getCost(), super.getPP(), discountString);
    }
}
