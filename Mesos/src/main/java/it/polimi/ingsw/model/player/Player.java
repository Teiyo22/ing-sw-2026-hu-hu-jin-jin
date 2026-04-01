package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.building.Building;

import java.util.List;

public class Player implements Comparable<Player>{
    private final String name;
    private final Totem totem;
    private final Tribe tribe;
    private final List<Building> buildings;

    private int food;
    private int pp;
    private int rank;

    private boolean noLossRitualMod;
    private boolean doubleRitualMod;

    public Player(String name, Totem totem) {
        this.name = name;
        this.totem = totem;
    }

    public void addPP(int delta) {

    }

    public void addFood(int delta) {

    }

    public void addBuilding(Building building) {

    }

    public int getFood() {
        return food;
    }

    public int getPP() {
        return pp;
    }

    public Tribe getTribe() {
        return tribe;
    }

    @Override
    public int compareTo(Player other) {
        return 0;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
