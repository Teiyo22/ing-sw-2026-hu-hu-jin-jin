package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.building.AbstractBuilding;

import java.util.List;

public class Player implements Comparable<Player>{
    private final String name;
    private final Totem totem;

    transient private int rank = 0;
    transient private Tribe tribe = null;

    /**
     * Constructs a new {@code Player} with the given name and totem.
     *
     * @param name  the player's display name
     * @param totem the totem associated with the player
     */
    public Player(String name, Totem totem) {
        this.name = name;
        this.totem = totem;
    }

    /**
     * Initializes the player's tribe with the given initial food.
     *
     * @param initialFood initial food of the player that is based on the starting order.
     * */
    public void initTribe(int initialFood) {
        tribe = new Tribe();
        tribe.setFood(initialFood);
    }

    /**
     * Adds the given amount of Prestige Points (PP) to the player's tribe.
     * The value of {@code delta} may be negative to subtract points.
     *
     * @param delta the amount of PP to add (can be negative)
     */
    public void addPP(int delta) {
        tribe.addPP(delta);
    }

    /**
     * Adds the given amount of food to the player's tribe.
     * The value of {@code delta} may be negative to consume food.
     *
     * @param delta the amount of food to add (can be negative)
     */
    public void addFood(int delta) {
        tribe.addFood(delta);
    }

    public void setFood(int food) {
        tribe.setFood(food);
    }

    /**
     * Enables "no loss ritual" modifier on the player's tribe.
     * When active, the tribe does not suffer losses during rituals.
     */
    public void enableNoLossRitualMod() {
        tribe.setNoLossRitualMod(true);
    }

    /**
     * Enables the "double ritual" modifier on the player's tribe.
     * When active, ritual event bonus pp are doubled.
     */
    public void enableDoubleRitualMod() {
        tribe.setDoubleRitualMod(true);
    }

    public void addBuilding(AbstractBuilding building) {
        tribe.addBuilding(building);
    }


    public List<AbstractBuilding> getBuildings() {
        return tribe.getBuildings();
    }

    public int getFood() {
        return tribe.getFood();
    }

    public int getPP() {
        return tribe.getPP();
    }

    public Tribe getTribe() {
        return tribe;
    }

    public String getName(){return name;}

    public boolean getNoLossRitualMod() {
        return tribe.getNoLossRitualMod();
    }

    public boolean getDoubleRitualMod() {
        return tribe.getDoubleRitualMod();
    }

    /**
     * Compares this player with another player for ordering.
     * Players are ordered in descending order of Prestige Points (PP).
     * Ties are broken by the amount of food, again in descending order.
     * If the amount of food is also equal, the players' ranks are also equal.
     *
     * @param other the player to be compared with
     * @return a negative integer, zero, or a positive integer as this player
     * ranks higher than, equal to, or lower than the specified player
     */
    @Override
    public int compareTo(Player other) {
        int res = Integer.compare(other.getPP(), tribe.getPP());

        if(res != 0)
            return res;

        res = Integer.compare(other.getFood(), tribe.getFood());

        return res;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
