package it.polimi.ingsw.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.building.cardPick.InventorPairBuilding;
import it.polimi.ingsw.model.card.building.cardPick.NewFullSetBuilding;
import it.polimi.ingsw.model.card.building.cardPick.SustenanceDiscountBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.BonusPPBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.BuilderBonusBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.CharacterBonusBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.FullSetBuilding;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.card.event.CavePainting;
import it.polimi.ingsw.model.card.event.Hunt;
import it.polimi.ingsw.model.card.event.ShamanicRitual;
import it.polimi.ingsw.model.card.event.Sustenance;

import java.lang.reflect.Type;

public class CardDeserializer implements JsonDeserializer<AbstractCard> {
    /**
     * Provides the information about how to deserialize a card from json depending on its type.
     * */
    @Override
    public AbstractCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "Boatwright" ->  context.deserialize(jsonObject, Inventor.class);
            case "Fletcher" ->  context.deserialize(jsonObject, Inventor.class);
            case "Fisherman" ->  context.deserialize(jsonObject, Inventor.class);
            case "Jeweler" ->  context.deserialize(jsonObject, Inventor.class);
            case "Herborist" ->  context.deserialize(jsonObject, Inventor.class);
            case "Shepherd" ->  context.deserialize(jsonObject, Inventor.class);
            case "Manufacturer" ->  context.deserialize(jsonObject, Inventor.class);
            case "Musician" ->  context.deserialize(jsonObject, Inventor.class);
            case "Tanner" ->  context.deserialize(jsonObject, Inventor.class);
            case "Baker" ->  context.deserialize(jsonObject, Inventor.class);
            case "Shaman1" ->  context.deserialize(jsonObject, Shaman.class);
            case "Shaman2" ->  context.deserialize(jsonObject, Shaman.class);
            case "Shaman3" ->  context.deserialize(jsonObject, Shaman.class);
            case "Artist" ->  context.deserialize(jsonObject, Artist.class);
            case "Collector" ->  context.deserialize(jsonObject, Collector.class);
            case "Builder1" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder2" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder3" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder4" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder5" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder6" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder7" ->  context.deserialize(jsonObject, Builder.class);
            case "Builder8" ->  context.deserialize(jsonObject, Builder.class);
            case "Hunter" ->  context.deserialize(jsonObject, Hunter.class);
            case "HunterWithIcon" ->  context.deserialize(jsonObject, Hunter.class);

            case "Hunt1" ->  context.deserialize(jsonObject, Hunt.class);
            case "Hunt2" ->  context.deserialize(jsonObject, Hunt.class);
            case "Hunt3" ->  context.deserialize(jsonObject, Hunt.class);
            case "ShamanicRitual1" ->  context.deserialize(jsonObject, ShamanicRitual.class);
            case "ShamanicRitual2" ->  context.deserialize(jsonObject, ShamanicRitual.class);
            case "ShamanicRitual3" ->  context.deserialize(jsonObject, ShamanicRitual.class);
            case "CavePainting1" ->  context.deserialize(jsonObject, CavePainting.class);
            case "CavePainting2" ->  context.deserialize(jsonObject, CavePainting.class);
            case "CavePainting3" ->  context.deserialize(jsonObject, CavePainting.class);
            case "Sustenance1" ->  context.deserialize(jsonObject, Sustenance.class);
            case "Sustenance2" ->  context.deserialize(jsonObject, Sustenance.class);
            case "Sustenance3" ->  context.deserialize(jsonObject, Sustenance.class);

            case "InventorPairBuilding" ->  context.deserialize(jsonObject, InventorPairBuilding.class);
            case "NewFullSetBuilding" ->  context.deserialize(jsonObject, NewFullSetBuilding.class);
            case "CollectorSustenanceDiscountBuilding" ->  context.deserialize(jsonObject, SustenanceDiscountBuilding.class);
            case "ArtistSustenanceDiscountBuilding" ->  context.deserialize(jsonObject, SustenanceDiscountBuilding.class);
            case "InventorSustenanceDiscountBuilding" ->  context.deserialize(jsonObject, SustenanceDiscountBuilding.class);
            case "BonusPPBuilding" ->  context.deserialize(jsonObject, BonusPPBuilding.class);
            case "BuilderDoublePPBuilding" ->  context.deserialize(jsonObject, BuilderBonusBuilding.class);
            case "InventorBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "ShamanBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "CollectorBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "ArtistBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "HunterBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "BuilderBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "FullSetBuilding" ->  context.deserialize(jsonObject, FullSetBuilding.class);
            case "CavePaintingBuilding" ->  context.deserialize(jsonObject, CavePaintingBuilding.class);
            case "ExtraActionBuilding" ->  context.deserialize(jsonObject, ExtraActionBuilding.class);
            case "HuntBuilding" ->  context.deserialize(jsonObject, HuntBuilding.class);
            case "OrderTileBuilding" ->  context.deserialize(jsonObject, OrderTileBuilding.class);
            case "RitualDoubleBonusBuilding" ->  context.deserialize(jsonObject, RitualDoubleBonusBuilding.class);
            case "RitualNoLossBuilding" ->  context.deserialize(jsonObject, RitualNoLossBuilding.class);
            case "RitualStarsBuilding" ->  context.deserialize(jsonObject, RitualStarsBuilding.class);
            default -> throw new JsonParseException("Card type not found: " + type);
        };
    }
}
