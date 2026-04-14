package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.player.Tribe;
import java.util.ArrayList;

import java.util.List;

public class Player implements Comparable<Player>{
    private final String name;
    private final Totem totem;
    private final Tribe tribe;
    private final List<AbstractBuilding> buildings;

    private int food;
    private int pp;
    private int rank;

    private boolean noLossRitualMod;
    private boolean doubleRitualMod;

    public Player(String name, Totem totem) {
        this.name = name;
        this.totem = totem;

        this.tribe = new Tribe();
        this.buildings = new ArrayList<>();
        this.food = 0;
        this.pp = 0;
        this.rank = 0;
    }

    public void addPP(int delta) {
        pp += delta;
    }

    public void addFood(int delta) {
        food += delta;
    }

    public void setFood(int n){
        food = n;
    }

    public void setNoLossRitualMod(boolean b) { noLossRitualMod = b; }

    public void setDoubleRitualMod(boolean b) { doubleRitualMod = b; }

    public void addBuilding(AbstractBuilding building) {
        buildings.add(building);
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

    public boolean getNoLossRitualMod() { return noLossRitualMod; }

    public boolean getDoubleRitualMod() { return doubleRitualMod; }

    @Override
    public int compareTo(Player other) {
        int res = Integer.compare(other.getPP(), this.pp);

        if(res != 0)
            return res;

        res = Integer.compare(other.getFood(), this.food);

        return res;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
