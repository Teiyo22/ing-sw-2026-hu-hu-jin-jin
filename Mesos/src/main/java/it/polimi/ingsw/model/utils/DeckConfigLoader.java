package it.polimi.ingsw.model.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.card.AbstractCard;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DeckConfigLoader {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbstractCard.class, new CardDeserializer())
            .create();

    public DeckConfig load(String filePath) {
        try(InputStream i = new FileInputStream(filePath)) {
            Reader reader = new InputStreamReader(i, StandardCharsets.UTF_8);

            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            return gson.fromJson(root, DeckConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
