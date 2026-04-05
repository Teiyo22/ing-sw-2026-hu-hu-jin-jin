package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.card.character.Shaman;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// aspetta la risposta di nicco
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

    public int getInventorCount() {
        int totInventors = 0;
        for(int count : inventors.values()){
            totInventors += count;
        }
        return totInventors;
    }

    public int getShamanCount() {
        int totShamans = 0;
        for(int i=0; i<3 ; i++ )
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
        for(Builder builder : builders){
            totDiscount+=builder.getBuildingDiscount();
        }
        return totDiscount;
    }

    public int getSustenanceDiscount() {
        return sustenanceDiscount;
    }
/**
 * Get the total stars possesed by the player
 * Every shaman card has a number of stars, so we multiply that number with the number shamans with the same stars and sum them all */
    public int getStars() {
        int totStars = 0;
        for(int i=0; i<3 ; i++ ){
            totStars= shamans[i] * (i+1);
        }
        return totStars;
    }
/**
 * So the invetor bonus PP is based on the number of inventors moltiplied for the numbers of types
 */
    public int getInventorBonusPP() {
        int types = inventors.size();
        return getInventorCount() * types;
    }
/**
 * Get the builder bonus PP
 * Every builder has his own bonus PP, so we sum all the bonuses we have
 * */
    public int getBuilderBonusPP() {
        int totalBonus = 0;
        for(Builder builder : builders){
            totalBonus += builder.getBonusPP();
        }
    }
/**
 * Add an inventor in the tribe
 * @param invetor , the inventor to add
 * it checks the type of the inventor, if true it add 1 to the value, if false it adds a new type with value 1*/
    public void addInventor(Inventor inventor) {
        InvetorType type = inventor.getInventorType();
        if(inventors.containsKey(type)){
            int oldValue = inventors.get(type);
            inventors.put(type, oldValue + 1);
        }else {
            inventors.put(type,1);
        }
    }
/**
 * It does the same thing as the inventor, but instead of types we now have the number of stars*/
    public void addShaman(Shaman shaman) {
        int shamanStars = shaman.getStar();
        shamans[shamanStars]++;
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
}
