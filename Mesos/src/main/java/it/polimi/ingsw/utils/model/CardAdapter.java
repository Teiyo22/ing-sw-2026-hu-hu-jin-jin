package it.polimi.ingsw.utils.model;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;

import java.lang.reflect.Type;
import java.util.Map;

public class CardAdapter<T extends AbstractCard> implements JsonSerializer<T>, JsonDeserializer<T> {

    private final Map<String, Class<? extends T>> typeMap;

    public CardAdapter(Map<String, Class<? extends T>> typeMap) {
        this.typeMap = typeMap;
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = context.serialize(src, src.getClass()).getAsJsonObject();
        json.addProperty("type", src.getClass().getSimpleName());
        return json;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String typeName = obj.get("type").getAsString();

        Class<? extends T> clazz = typeMap.get(typeName);
        if (clazz == null) throw new JsonParseException("Unknown type: " + typeName);
        return context.deserialize(obj, clazz);
    }
}
