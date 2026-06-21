package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;

public class SustenanceDiscountBuilding extends AbstractBuilding implements VisitableBuilding {
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

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    public int getInventorDiscount(){
        return inventorDiscount;
    }

    public int getShamanDiscount(){
        return shamanDiscount;
    }

    public int getBuilderDiscount(){
        return builderDiscount;
    }

    public int getArtistDiscount(){
        return artistDiscount;
    }

    public int getHunterDiscount(){
        return hunterDiscount;
    }

    public int getCollectorDiscount(){
        return collectorDiscount;
    }

    @Override
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        if (buildingHandler !=  null)
            buildingHandler.addSustenanceDiscountBuildings(this);
    }

    @Override
    public String toString() {
        List<String> discounts = Map.of(
                        "Inventor", inventorDiscount,
                        "Shaman", shamanDiscount,
                        "Hunter", hunterDiscount,
                        "Collector", collectorDiscount,
                        "Artist", artistDiscount,
                        "Builder", builderDiscount).entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(e -> "Discount (" + e.getKey() + "): " + e.getValue())
                .toList();

        String format = "| %-25s ".repeat(discounts.size());

        return super.toString() + String.format(format, discounts);
    }
}
