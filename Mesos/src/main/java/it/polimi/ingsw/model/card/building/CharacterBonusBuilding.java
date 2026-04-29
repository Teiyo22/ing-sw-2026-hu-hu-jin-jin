package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterBonusBuilding extends AbstractBuilding implements VisitableBuilding {
    @Expose private int inventorBonusPP;
    @Expose private int shamanBonusPP;
    @Expose private int hunterBonusPP;
    @Expose private int collectorBonusPP;
    @Expose private int artistBonusPP;
    @Expose private int builderBonusPP;

    public CharacterBonusBuilding(String type, int era, boolean isFinal, int cost, int pp,
                                  int inventorBonusPP, int shamanBonusPP, int hunterBonusPP,
                                  int collectorBonusPP, int artistBonusPP, int builderBonusPP) {
        super(type, era, isFinal, cost, pp);
        this.inventorBonusPP = inventorBonusPP;
        this.shamanBonusPP = shamanBonusPP;
        this.hunterBonusPP = hunterBonusPP;
        this.collectorBonusPP = collectorBonusPP;
        this.artistBonusPP = artistBonusPP;
        this.builderBonusPP = builderBonusPP;
    }

    public CharacterBonusBuilding(CharacterBonusBuilding source) {
        super(source);
        this.inventorBonusPP = source.inventorBonusPP;
        this.shamanBonusPP = source.shamanBonusPP;
        this.hunterBonusPP = source.hunterBonusPP;
        this.collectorBonusPP = source.collectorBonusPP;
        this.artistBonusPP = source.artistBonusPP;
        this.builderBonusPP = source.builderBonusPP;
    }

    @Override
    public AbstractCard clone() {
        return new CharacterBonusBuilding(this);
    }

    public int getInventorBonusPP() {
        return inventorBonusPP;
    }

    public int getShamanBonusPP() {
        return shamanBonusPP;
    }

    public int getHunterBonusPP() {
        return hunterBonusPP;
    }

    public int getCollectorBonusPP() {
        return collectorBonusPP;
    }

    public int getArtistBonusPP() {
        return artistBonusPP;
    }

    public int getBuilderBonusPP() {
        return builderBonusPP;
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public String toString() {
        String res = String.format("[ Type: %s  |  Era: %d  |  Cost: %d  |  PP: %d  |  Bonus PP for character type: ",
                super.getType(), super.getEra(), super.getCost(), super.getPP());

        Map<String, Integer> bonuses = new HashMap<>();
        bonuses.put("Inventor", inventorBonusPP);
        bonuses.put("Shaman", shamanBonusPP);
        bonuses.put("Hunter", hunterBonusPP);
        bonuses.put("Collector", collectorBonusPP);
        bonuses.put("Artist", artistBonusPP);
        bonuses.put("Builder", builderBonusPP);

        bonuses = bonuses.entrySet().stream().filter(e -> e.getValue() > 0).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        for(String key : bonuses.keySet()) {
            res += key + " +" + bonuses.get(key);
        }
        res += " ]";

        return res;
    }
}
