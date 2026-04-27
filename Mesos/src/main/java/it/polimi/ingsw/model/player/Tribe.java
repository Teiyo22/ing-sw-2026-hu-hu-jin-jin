package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.card.character.Shaman;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tribe {
    private int food;
    private int pp;

    private final List<AbstractBuilding> buildings;

    private final Map<InventorType, Integer> inventors;
    private final List<Builder> builders;
    private final int[] shamans;
    private final int[] hunters;
    private int collectors;
    private int artists;

    private int stars;
    private int sustenanceDiscount;

    private boolean noLossRitualMod;
    private boolean doubleRitualMod;

    public Tribe() {
        this.food = 0;
        this.pp = 0;

        this.buildings = new ArrayList<>();

        this.inventors = new HashMap<>();
        this.builders = new ArrayList<>();
        this.hunters = new int[2];
        this.shamans = new int[3];
        this.collectors = 0;
        this.artists = 0;

        this.stars = 0;
        this.sustenanceDiscount = 0;
    }

    public int getTribeSize() {
        return getArtistCount() + getBuilderCount() + getCollectorCount() + getHunterCount() + getInventorCount() + getShamanCount();
    }

    public int getInventorCount() {
        return inventors.size();
    }

    public int getNumInventorType(InventorType type) {
        return inventors.get(type);
    }

    public int getShamanCount() {
        int totShamans = 0;
        for (int i = 0; i < 3; i++)
            totShamans += shamans[i];
        return totShamans;
    }

    public int getBuilderCount() {
        return builders.size();
    }

    public int getCollectorCount() {
        return collectors;
    }

    public int getHunterCount() {
        return hunters[0] + hunters[1];
    }

    public int getArtistCount() {
        return artists;
    }

    /**
     * Get the discount provided by builders.
     * The discount is the sum of all the discounts provided by the builders.
     */
    public int getBuilderDiscount() {
        int totDiscount = 0;
        for (Builder builder : builders) {
            totDiscount += builder.getBuildingDiscount();
        }
        return totDiscount;
    }

    public int getSustenanceDiscount() {
        return sustenanceDiscount;
    }

    /**
     * Add the provided discount to the total discount.
     *
     * @param discount is provided by either the collectors or the sustenance building.
     */
    public void addSustenanceDiscount(int discount) {
        this.sustenanceDiscount += discount;
    }

    /**
     * Add the provided number of stars to the total stars.
     *
     * @param stars is provided by either the shamans or the bonus stars building.
     * */
    public void addStars(int stars) {
        this.stars += stars;
    }


    public int getStars() {
        return stars;
    }

    /**
     * Get the bonus PP provided by the inventors.
     * The bonus PP is the product of the number of unique inventor types and the number of inventors.
     */
    public int getInventorBonusPP() {
        int types = inventors.size();
        return getInventorCount() * types;
    }

    /**
     * Get the builder bonus PP
     * Every builder has a bonus PP that is added to the total PP at the end of the game.
     */
    public int getBuilderBonusPP() {
        int totalBonus = 0;
        for (Builder builder : builders) {
            totalBonus += builder.getBonusPP();
        }
        return totalBonus;
    }

    /**
     * Add an inventor in the tribe.
     * If {@link InventorType} is already present, increment the number of inventors, otherwise add it with value 1.
     */
    public void addInventor(Inventor inventor) {
        InventorType type = inventor.getInventorType();
        int value = 1;

        if(inventors.containsKey(type))
            value = inventors.get(type) + 1;
        inventors.put(type, value);
    }

    /**
     * Add shaman based on the number of stars.
     */
    public void addShaman(Shaman shaman) {
        int shamanStars = shaman.getStar();
        shamans[shamanStars-1]++;
    }

    public void addBuilder(Builder builder) {
        builders.add(builder);
    }

    public void addCollector() {
        collectors++;
    }

    /**
     * Add a hunter based on the presence of the icon.
     * */
    public void addHunter(boolean hasIcon) {
        if(hasIcon)
            hunters[0]++;
        else
            hunters[1]++;
    }

    public void addArtist() {
        artists++;
    }

    /**
     * Return the character with the minimum count.
     */
    public int getMinChar() {
        int min = getInventorCount();
        if (getShamanCount() < min) {
            min = getShamanCount();
        }
        if (getHunterCount() < min) {
            min = getHunterCount();
        }
        if (getArtistCount() < min) {
            min = getArtistCount();
        }
        if (getBuilderCount() < min) {
            min = getBuilderCount();
        }
        if (getCollectorCount() < min) {
            min = getCollectorCount();
        }
        return min;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void addFood(int foodDelta) {
        this.food += foodDelta;
    }

    public int getPP() {
        return pp;
    }

    public void addPP(int ppDelta) {
        this.pp += ppDelta;
    }

    public List<AbstractBuilding> getBuildings() {
        return buildings;
    }

    public void addBuilding(AbstractBuilding building) {
        buildings.add(building);
    }

    public void setNoLossRitualMod(boolean b) {
        noLossRitualMod = b;
    }

    public void setDoubleRitualMod(boolean b) {
        doubleRitualMod = b;
    }

    public boolean getNoLossRitualMod() {
        return noLossRitualMod;
    }

    public boolean getDoubleRitualMod() {
        return doubleRitualMod;
    }
}
