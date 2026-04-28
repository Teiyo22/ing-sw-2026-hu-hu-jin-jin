package it.polimi.ingsw.utils.model;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.building.InventorPairBuilding;
import it.polimi.ingsw.model.card.building.NewFullSetBuilding;
import it.polimi.ingsw.model.card.building.SustenanceDiscountBuilding;
import it.polimi.ingsw.model.card.building.BonusPPBuilding;
import it.polimi.ingsw.model.card.building.BuilderBonusBuilding;
import it.polimi.ingsw.model.card.building.CharacterBonusBuilding;
import it.polimi.ingsw.model.card.building.FullSetBuilding;
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
            case "Inventor" ->  context.deserialize(jsonObject, Inventor.class);
            case "Shaman" ->  context.deserialize(jsonObject, Shaman.class);
            case "Artist" ->  context.deserialize(jsonObject, Artist.class);
            case "Collector" ->  context.deserialize(jsonObject, Collector.class);
            case "Builder" ->  context.deserialize(jsonObject, Builder.class);
            case "Hunter" ->  context.deserialize(jsonObject, Hunter.class);
            case "Hunt" ->  context.deserialize(jsonObject, Hunt.class);
            case "ShamanicRitual" ->  context.deserialize(jsonObject, ShamanicRitual.class);
            case "CavePainting" ->  context.deserialize(jsonObject, CavePainting.class);
            case "Sustenance" ->  context.deserialize(jsonObject, Sustenance.class);
            case "InventorPairBuilding" ->  context.deserialize(jsonObject, InventorPairBuilding.class);
            case "NewFullSetBuilding" ->  context.deserialize(jsonObject, NewFullSetBuilding.class);
            case "SustenanceDiscountBuilding" ->  context.deserialize(jsonObject, SustenanceDiscountBuilding.class);
            case "BonusPPBuilding" ->  context.deserialize(jsonObject, BonusPPBuilding.class);
            case "BuilderDoublePPBuilding" ->  context.deserialize(jsonObject, BuilderBonusBuilding.class);
            case "CharacterBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
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
