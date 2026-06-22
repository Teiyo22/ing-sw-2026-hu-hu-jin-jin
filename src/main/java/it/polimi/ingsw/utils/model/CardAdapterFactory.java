package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.card.event.CavePainting;
import it.polimi.ingsw.model.card.event.Hunt;
import it.polimi.ingsw.model.card.event.ShamanicRitual;
import it.polimi.ingsw.model.card.event.Sustenance;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class CardAdapterFactory<T extends AbstractCard> {
    private final static Map<String, Class<? extends AbstractCard>> types = Map.ofEntries(
            entry("BonusPPBuilding", BonusPPBuilding.class),
            entry("BuilderDoublePPBuilding", BuilderDoublePPBuilding.class),
            entry("CavePaintingBuilding", CavePaintingBuilding.class),
            entry("CharacterBonusBuilding", CharacterBonusBuilding.class),
            entry("ExtraActionBuilding", ExtraActionBuilding.class),
            entry("FullSetBuilding", FullSetBuilding.class),
            entry("HuntBuilding", HuntBuilding.class),
            entry("InventorPairBuilding", InventorPairBuilding.class),
            entry("NewFullSetBuilding", NewFullSetBuilding.class),
            entry("OrderTileBuilding", OrderTileBuilding.class),
            entry("RitualDoubleBonusBuilding", RitualDoubleBonusBuilding.class),
            entry("RitualNoLossBuilding", RitualNoLossBuilding.class),
            entry("RitualStarsBuilding", RitualStarsBuilding.class),
            entry("SustenanceDiscountBuilding", SustenanceDiscountBuilding.class),
            entry("Artist", Artist.class),
            entry("Builder", Builder.class),
            entry("Collector", Collector.class),
            entry("Hunter", Hunter.class),
            entry("Inventor", Inventor.class),
            entry("Shaman", Shaman.class),
            entry("CavePainting", CavePainting.class),
            entry("Hunt", Hunt.class),
            entry("ShamanicRitual", ShamanicRitual.class),
            entry("Sustenance", Sustenance.class)
    );

    public CardAdapter<T> create(Class<T> base) {
        Map<String, Class<? extends T>> subtypes = types.entrySet().stream()
                .filter(e -> base.isAssignableFrom(e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ((Class<? extends T>) e.getValue())
                ));

        return new CardAdapter<>(subtypes);
    }


}
