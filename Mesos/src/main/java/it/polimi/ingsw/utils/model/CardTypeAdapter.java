package it.polimi.ingsw.utils.model;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;

import java.lang.reflect.Type;

public class CardTypeAdapter implements JsonSerializer<AbstractCard>, JsonDeserializer<AbstractCard> {
    private final CardTypeRegistry registry;
    private final Gson delegateGson = new Gson();

    public CardTypeAdapter(CardTypeRegistry registry) {
        this.registry = registry;
    }

    @Override
    public JsonElement serialize(AbstractCard src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = delegateGson.toJsonTree(src).getAsJsonObject();

        jsonObject.addProperty("type", registry.resolve(src.getClass()));

        return jsonObject;
    }

    @Override
    public AbstractCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeName = jsonObject.get("type").getAsString();

        Class<? extends AbstractCard> concreteClass = registry.resolve(typeName);

        return delegateGson.fromJson(jsonObject, concreteClass);
    }
}
