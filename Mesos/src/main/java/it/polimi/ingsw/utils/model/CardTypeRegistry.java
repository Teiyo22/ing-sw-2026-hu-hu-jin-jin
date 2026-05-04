package it.polimi.ingsw.utils.model;

import com.google.gson.JsonParseException;
import it.polimi.ingsw.model.card.AbstractCard;

import java.util.HashMap;
import java.util.Map;

public class CardTypeRegistry {
    private final Class<?> baseType;
    private final Map<String, Class<? extends AbstractCard>> typeMap = new HashMap<>();

    public CardTypeRegistry(Class<?> baseType) {
        this.baseType = baseType;
    }

    public void register(String typeName, Class<? extends AbstractCard> typeClass) {
        if (baseType.isAssignableFrom(typeClass))
            typeMap.put(typeName, typeClass);
    }

    public Class<? extends AbstractCard> resolve(String typeName) {
        Class<? extends AbstractCard> typeClass = typeMap.get(typeName);

        if (typeClass == null)
            throw new JsonParseException("Unknown type: " + typeName);

        return typeClass;
    }

    public String resolve(Class<?> typeClass) {
        for (Map.Entry<String, Class<? extends AbstractCard>> entry : typeMap.entrySet())

            if (entry.getValue() == typeClass)
                return entry.getKey();
        throw new JsonParseException("Unknown type: " + typeClass);
    }
}
