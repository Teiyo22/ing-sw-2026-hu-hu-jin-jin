package it.polimi.ingsw.utils.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.card.event.CavePainting;
import it.polimi.ingsw.model.card.event.Hunt;
import it.polimi.ingsw.model.card.event.ShamanicRitual;
import it.polimi.ingsw.model.card.event.Sustenance;

public class CardTypeAdapterFactory {
    public static CardTypeAdapter create(Class<? extends AbstractCard> baseTypeClass) {
        CardTypeRegistry cardRegistry = new CardTypeRegistry(baseTypeClass);
        cardRegistry.register("Inventor", Inventor.class);
        cardRegistry.register("Shaman", Shaman.class);
        cardRegistry.register("Artist", Artist.class);
        cardRegistry.register("Collector", Collector.class);
        cardRegistry.register("Builder", Builder.class);
        cardRegistry.register("Hunter", Hunter.class);
        cardRegistry.register("Hunt", Hunt.class);
        cardRegistry.register("ShamanicRitual", ShamanicRitual.class);
        cardRegistry.register("CavePainting", CavePainting.class);
        cardRegistry.register("Sustenance", Sustenance.class);
        cardRegistry.register("InventorPairBuilding", InventorPairBuilding.class);
        cardRegistry.register("NewFullSetBuilding", NewFullSetBuilding.class);
        cardRegistry.register("SustenanceDiscountBuilding", SustenanceDiscountBuilding.class);
        cardRegistry.register("BonusPPBuilding", BonusPPBuilding.class);
        cardRegistry.register("BuilderDoublePPBuilding", BuilderBonusBuilding.class);
        cardRegistry.register("CharacterBonusBuilding", CharacterBonusBuilding.class);
        cardRegistry.register("FullSetBuilding", FullSetBuilding.class);
        cardRegistry.register("CavePaintingBuilding", CavePaintingBuilding.class);
        cardRegistry.register("ExtraActionBuilding", ExtraActionBuilding.class);
        cardRegistry.register("HuntBuilding", HuntBuilding.class);
        cardRegistry.register("OrderTileBuilding", OrderTileBuilding.class);
        cardRegistry.register("RitualDoubleBonusBuilding", RitualDoubleBonusBuilding.class);
        cardRegistry.register("RitualNoLossBuilding", RitualNoLossBuilding.class);
        cardRegistry.register("RitualStarsBuilding", RitualStarsBuilding.class);

        return new CardTypeAdapter(cardRegistry);
    }
}