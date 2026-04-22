package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.player.Tribe;
import java.util.ArrayList;

import java.util.List;

public class Player implements Comparable<Player>{
    private final String name;
    private final Totem totem;
    private int rank;

    private final Tribe tribe;

    public Player(String name, Totem totem) {
        this.name = name;
        this.totem = totem;

        this.tribe = new Tribe();
        this.rank = 0;
    }

    public void addPP(int delta) {
        tribe.addPP(delta);
    }

    public void addFood(int delta) {
        tribe.addFood(delta);
    }

    public void setFood(int n){
        tribe.setFood(n);
    }

    public void setNoLossRitualMod(boolean b) {
        tribe.setNoLossRitualMod(b);
    }

    public void setDoubleRitualMod(boolean b) {
        tribe.setDoubleRitualMod(b);
    }

    public void addBuilding(AbstractBuilding building) {
        tribe.addBuilding(building);
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

    public boolean getNoLossRitualMod() {
        return tribe.getNoLossRitualMod();
    }

    public boolean getDoubleRitualMod() {
        return tribe.getDoubleRitualMod();
    }

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
