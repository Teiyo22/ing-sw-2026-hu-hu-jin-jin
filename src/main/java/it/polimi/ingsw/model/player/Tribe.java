package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tribe implements Serializable {
    private int food;
    private int pp;

    private List<AbstractBuilding> buildings;

    private Map<InventorType, List<Inventor>> inventors;
    private List<Builder> builders;
    private List<Shaman> shamans;
    private List<Hunter> hunters;
    private List<Collector> collectors;
    private List<Artist> artists;

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
        this.hunters = new ArrayList<>();
        this.shamans = new ArrayList<>();
        this.collectors = new ArrayList<>();
        this.artists = new ArrayList<>();

        this.stars = 0;
        this.sustenanceDiscount = 0;
        this.noLossRitualMod = false;
        this.doubleRitualMod = false;
    }

    public Tribe shallowCopy() {
        Tribe tribe = new Tribe();
        tribe.food = this.food;
        tribe.pp = this.pp;

        tribe.stars = this.stars;
        tribe.sustenanceDiscount = this.sustenanceDiscount;
        tribe.noLossRitualMod = this.noLossRitualMod;
        tribe.doubleRitualMod = this.doubleRitualMod;

        return tribe;
    }

    public Tribe deepCopy() {
        Tribe tribe = shallowCopy();
        tribe.buildings = new ArrayList<>(this.buildings);
        tribe.inventors = new HashMap<>(this.inventors);
        tribe.builders = new ArrayList<>(this.builders);
        tribe.hunters = new ArrayList<>(this.hunters);
        tribe.shamans = new ArrayList<>(this.shamans);
        tribe.collectors = new ArrayList<>(this.collectors);
        tribe.artists = new ArrayList<>(this.artists);

        return tribe;
    }

    // ================================================================
    // Inventor related methods
    // ================================================================

    public int getInventorCount() {
        return inventors.values().stream()
            .mapToInt(List::size)
            .sum();
    }

    public int getNumInventorType(InventorType type) {
        return inventors.getOrDefault(type, null) == null
            ? 0
            : inventors.get(type).size();
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
     * Add an inventor in the tribe.
     * If {@link InventorType} is already present, increment the number of inventors, otherwise add it with value 1.
     */
    public void addInventor(Inventor inventor) {
        InventorType type = inventor.getInventorType();

        List<Inventor> inventorList = inventors.getOrDefault(type, new ArrayList<>());
        inventorList.add(inventor);
        inventors.put(type, inventorList);
    }

    public int getUniqueInventorsCount(){
        return this.inventors.size();
    }

    public List<Inventor> getInventors() {
        return inventors.values().stream()
            .flatMap(List::stream)
            .toList();
    }

    // ================================================================
    // Shaman related methods
    // ================================================================

    public int getShamanCount() {
        return shamans.size();
    }

    /**
     * Add shaman based on the number of stars.
     */
    public void addShaman(Shaman shaman) {
        shamans.add(shaman);
    }

    public List<Shaman> getShamans() {
        return new ArrayList<>(shamans);
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

    public void setStars(int stars) {
        this.stars = stars;
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

    // ================================================================
    // Builder related methods
    // ================================================================

    public int getBuilderCount() {
        return builders.size();
    }

    /**
     * Get the discount provided by builders.
     * The discount is the sum of all the discounts provided by the builders.
     */
    public int getBuilderDiscount() {
        return builders.stream()
            .mapToInt(Builder::getBuildingDiscount)
            .sum();
    }

    public void addBuilder(Builder builder) {
        builders.add(builder);
    }

    public List<Builder> getBuilders() {
        return new ArrayList<>(builders);
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

    public List<AbstractBuilding> getBuildings() {
        return buildings;
    }

    public void addBuilding(AbstractBuilding building) {
        buildings.add(building);
    }

    // ================================================================
    // Hunter related methods
    // ================================================================

    public int getHunterCount() {
        return hunters.size();
    }

    /**
     * Add a hunter based on the presence of the icon.
     * */
    public void addHunter(Hunter hunter) {
        hunters.add(hunter);
    }

    public List<Hunter> getHunters() {
        return new ArrayList<>(hunters);
    }

    // ================================================================
    // Artist related methods
    // ================================================================

    public int getArtistCount() {
        return artists.size();
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    public List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    // ================================================================
    // Collector related methods
    // ================================================================

    public int getCollectorCount() {
        return collectors.size();
    }

    public void addCollector(Collector collector) {
        collectors.add(collector);
    }

    public List<Collector> getCollectors() {
        return new ArrayList<>(collectors);
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

    public void setSustenanceDiscount(int discount) {
        this.sustenanceDiscount = discount;
    }

    public int getTribeSize() {
        return getArtistCount() +
            getBuilderCount() +
            getCollectorCount() +
            getHunterCount() +
            getInventorCount() +
            getShamanCount();
    }

    // ================================================================
    // Other tribe methods
    // ================================================================

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

    public void setPP(int pp) {
        this.pp = pp;
    }

    public int getPP() {
        return pp;
    }

    public void addPP(int ppDelta) {
        this.pp += ppDelta;
    }
}
