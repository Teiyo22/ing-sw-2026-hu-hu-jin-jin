package it.polimi.ingsw.model.card.building.cardPick;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class SustenanceDiscountBuilding extends CardPickBuilding{
    @Expose int inventorDiscount;
    @Expose int shamanDiscount;
    @Expose int hunterDiscount;
    @Expose int collectorDiscount;
    @Expose int artistDiscount;
    @Expose int builderDiscount;

    public SustenanceDiscountBuilding(String type, int era, boolean isFinal,
                                      int cost, int pp, BuildingHandler buildingHandler, 
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

    public void onPick(Player player, BuildingHandler buildingHandler) {
        owner = player;
        buildingHandler.addCardPickBuilding(this);

        owner.getTribe().addSustenanceDiscount(
                owner.getTribe().getInventorCount() * inventorDiscount +
                owner.getTribe().getShamanCount() * shamanDiscount +
                owner.getTribe().getHunterCount() * hunterDiscount +
                owner.getTribe().getCollectorCount() * collectorDiscount +
                owner.getTribe().getArtistCount() * artistDiscount +
                owner.getTribe().getBuilderCount() * builderDiscount);
    }

    @Override
    public void doForInventor(Inventor i) {
        owner.getTribe().addSustenanceDiscount(inventorDiscount);
    }

    @Override
    public void doForShaman(Shaman s) {
        owner.getTribe().addSustenanceDiscount(shamanDiscount);
    }

    @Override
    public void doForHunter(Hunter h) {
        owner.getTribe().addSustenanceDiscount(hunterDiscount);
    }

    @Override
    public void doForCollector(Collector c) {
        owner.getTribe().addSustenanceDiscount(collectorDiscount);
    }

    @Override
    public void doForArtist(Artist a) {
        owner.getTribe().addSustenanceDiscount(artistDiscount);
    }

    @Override
    public void doForBuilder(Builder b) {
        owner.getTribe().addSustenanceDiscount(builderDiscount);
    }
}
