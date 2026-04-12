package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class SustenanceDiscountBuilding extends CardPickBuilding{
    int inventorDiscount;
    int shamanDiscount;
    int hunterDiscount;
    int collectorDiscount;
    int artistDiscount;
    int builderDiscount;

    public SustenanceDiscountBuilding(int era, int cost, int pp, BuildingHandler buildingHandler, int inventorDiscount, int shamanDiscount, int hunterDiscount, int artistDiscount, int collectorDiscount, int builderDiscount) {
        super(era, cost, pp, buildingHandler);
        this.inventorDiscount = inventorDiscount;
        this.shamanDiscount = shamanDiscount;
        this.hunterDiscount = hunterDiscount;
        this.collectorDiscount = collectorDiscount;
        this.artistDiscount = artistDiscount;
        this.builderDiscount = builderDiscount;
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addCardPickBuilding(this);

        int numInventors = owner.getTribe().getInventorCount();
        int numShamans = owner.getTribe().getShamanCount();
        int numHunters = owner.getTribe().getHunterCount();
        int numCollectors = owner.getTribe().getCollectorCount();
        int numArtists = owner.getTribe().getArtistCount();
        int numBuilders = owner.getTribe().getBuilderCount();
        owner.getTribe().addSustenanceDiscount(numInventors * inventorDiscount + numShamans * shamanDiscount + numHunters * hunterDiscount + numCollectors * collectorDiscount + numArtists * artistDiscount + numBuilders * builderDiscount);
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
