package it.polimi.ingsw.model.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.card.event.CavePainting;
import it.polimi.ingsw.model.card.event.Hunt;
import it.polimi.ingsw.model.card.event.ShamanicRitual;

import java.lang.reflect.Type;

public class CardDeserializer implements JsonDeserializer<AbstractCard> {
    @Override
    public AbstractCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "inventor" ->  context.deserialize(jsonObject, Inventor.class);
            case "shaman" ->  context.deserialize(jsonObject, Shaman.class);
            case "artist" ->  context.deserialize(jsonObject, Artist.class);
            case "collector" ->  context.deserialize(jsonObject, Collector.class);
            case "builder" ->  context.deserialize(jsonObject, Builder.class);
            case "hunter" ->  context.deserialize(jsonObject, Hunter.class);
            case "hunt" ->  context.deserialize(jsonObject, Hunt.class);
            case "ritual" ->  context.deserialize(jsonObject, ShamanicRitual.class);
            case "painting" ->  context.deserialize(jsonObject, CavePainting.class);
            default -> throw new JsonParseException("Card type not found: " + type);
        };
    }
}
