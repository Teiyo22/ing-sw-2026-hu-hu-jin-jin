package it.polimi.ingsw.utils.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.info.StateInfoType;

import java.lang.reflect.Type;

public class StateInfoSerializer implements JsonSerializer<ModelStateInfo> {
    @Override
    public JsonElement serialize(ModelStateInfo src, Type typeOfSrc, JsonSerializationContext context) {
        StateInfoType type = src.getType();

        return switch (type) {
            case CARD_PICK -> context.serialize(src, it.polimi.ingsw.controller.common.info.CardPickStateInfo.class);
            case OFFER_PICk -> context.serialize(src, it.polimi.ingsw.controller.common.info.OfferPickStateInfo.class);
            case GAME_END -> context.serialize(src, it.polimi.ingsw.controller.common.info.GameEndStateInfo.class);
        };
    }
}
