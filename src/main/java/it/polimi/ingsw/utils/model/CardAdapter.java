package it.polimi.ingsw.utils.model;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.utils.GenericGsonAdapter;

import java.lang.reflect.Type;
import java.util.Map;

public class CardAdapter<T extends AbstractCard> extends GenericGsonAdapter<T> {
    public CardAdapter(Map<String, Class<? extends T>> typeMap) {
        this.typeMap = typeMap;
    }
}
