package it.polimi.ingsw.model.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;

import com.google.gson.Gson;
import it.polimi.ingsw.model.card.AbstractCard;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigLoader {
    /**
     * Loads the offerTile from a config file. The loaded configuration file depends on the player count.
     * @param filePath the path to the file.
     * @return an array of orderTile.
     */
    public OfferTile[] loadOfferTile(String filePath){
        Gson gson= new Gson();

        try(InputStream is= getClass().getResourceAsStream(filePath)) {
            if(is == null) throw new RuntimeException(filePath + "not found");

            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            return gson.fromJson(reader,OfferTile[].class);
        } catch (IOException e) {
            throw new RuntimeException("Error"+filePath,e);
        }
    }

    /**
     * Loads the orderSlot from a config file. The loaded configuration file depends on the player count.
     * @param filePath is the path to the file.
     * @return an array of orderSlot.
     */
    public OrderSlot[] loadOrderSlot(String filePath){
        Gson gson= new Gson();

        try(InputStream is= getClass().getResourceAsStream(filePath)){
            if(is == null) throw new RuntimeException(filePath+"not found");

            InputStreamReader reader= new InputStreamReader(is, StandardCharsets.UTF_8);
            return gson.fromJson(reader, OrderSlot[].class);
        } catch (IOException e) {
            throw new RuntimeException("Error"+filePath,e);
        }
    }

    /**
     * Loads the deck configuration from a config file. The loaded configuration file depends on the player count.
     * @param filePath is the path to the file.
     * @return a deck configuration used to load the singular components of the deck.
     * */
    public DeckConfig loadDeckConfig(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AbstractCard.class, new CardDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        try(InputStream i = new FileInputStream(filePath)) {
            Reader reader = new InputStreamReader(i, StandardCharsets.UTF_8);

            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            return gson.fromJson(root, DeckConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
