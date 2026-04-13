package it.polimi.ingsw.model.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.building.cardPick.InventorPairBuilding;
import it.polimi.ingsw.model.card.building.cardPick.NewFullSetBuilding;
import it.polimi.ingsw.model.card.building.cardPick.SustenanceDiscountBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.BonusPPBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.BuilderBonusBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.CharacterBonusBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.FullSetBuilding;
import it.polimi.ingsw.model.card.character.*;

import java.lang.reflect.Type;

public class BuildingDeserializer implements JsonDeserializer<AbstractBuilding> {
    @Override
    public AbstractBuilding deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "inventorPairBuilding" ->  context.deserialize(jsonObject, InventorPairBuilding.class);
            case "newFullSetBuilding" ->  context.deserialize(jsonObject, NewFullSetBuilding.class);
            case "sustenanceDiscountBuilding" ->  context.deserialize(jsonObject, SustenanceDiscountBuilding.class);
            case "bonusPPBuilding" ->  context.deserialize(jsonObject, BonusPPBuilding.class);
            case "builderBonusBuilding" ->  context.deserialize(jsonObject, BuilderBonusBuilding.class);
            case "characterBonusBuilding" ->  context.deserialize(jsonObject, CharacterBonusBuilding.class);
            case "fullSetBuilding" ->  context.deserialize(jsonObject, FullSetBuilding.class);
            case "cavePaintingBuilding" ->  context.deserialize(jsonObject, CavePaintingBuilding.class);
            case "extraActionBuilding" ->  context.deserialize(jsonObject, ExtraActionBuilding.class);
            case "huntBuilding" ->  context.deserialize(jsonObject, HuntBuilding.class);
            case "offerTileBuilding" ->  context.deserialize(jsonObject, OrderTileBuilding.class);
            case "ritualDoubleBonusBuilding" ->  context.deserialize(jsonObject, RitualDoubleBonusBuilding.class);
            case "ritualNoLossBuilding" ->  context.deserialize(jsonObject, RitualNoLossBuilding.class);
            case "ritualStarsBuilding" ->  context.deserialize(jsonObject, RitualStarsBuilding.class);
            default -> throw new JsonParseException("Card type not found: " + type);
        };
    }
}
