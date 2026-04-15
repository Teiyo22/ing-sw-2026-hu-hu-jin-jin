package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.card.character.Shaman;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tribe {
    private final Map<InventorType, Integer> inventors;
    private final int[] shamans;
    private final List<Builder> builders;
    private int collectors;
    private int hunters;
    private int artists;

    private int stars;
    private int sustenanceDiscount;

    public Tribe() {
        this.inventors = new HashMap<>();
        this.shamans = new int[3];
        this.builders = new ArrayList<>();
        this.collectors = 0;
        this.hunters = 0;
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

    /**
     * Get the number of one type of inventor
     */
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
        return hunters;
    }

    public int getArtistCount() {
        return artists;
    }

    /**
     * Get the discount provided by builders
     * Each builder has his own discount so we sum them
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
     * Add discount in sustenanceDiscount
     */
    public void addSustenanceDiscount(int discount) {
        this.sustenanceDiscount += discount;
    }

    public void addStars(int stars) {
        this.stars += stars;
    }

    /**
     * Get the total stars possesed by the player
     * Every shaman card has a number of stars, so we multiply that number with the number shamans with the same stars and sum them all
     */
    public int getStars() {
        return stars;
    }

    /**
     * So the invetor bonus PP is based on the number of inventors moltiplied for the numbers of types
     */
    public int getInventorBonusPP() {
        int types = inventors.keySet().size();
        return getInventorCount() * types;
    }

    /**
     * Get the builder bonus PP
     * Every builder has his own bonus PP, so we sum all the bonuses we have
     *
     */
    public int getBuilderBonusPP() {
        int totalBonus = 0;
        for (Builder builder : builders) {
            totalBonus += builder.getBonusPP();
        }
        return totalBonus;
    }

    /**
     * Add an inventor in the tribe
     * it checks the type of the inventor, if true it add 1 to the value, if false it adds a new type with value 1
     */
    public void addInventor(Inventor inventor) {
        InventorType type = inventor.getInventorType();
        int value = 1;

        if(inventors.containsKey(type))
            value = inventors.get(type) + 1;
        inventors.put(type, value);
    }

    /**
     * Add shaman based on how many stars they have
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

    public void addHunter() {
        hunters++;
    }

    public void addArtist() {
        artists++;
    }

    /**
     * Return the Character with the minimun number of cards
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
}
