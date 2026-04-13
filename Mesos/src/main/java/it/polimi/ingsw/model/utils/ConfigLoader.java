package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigLoader {
    private final Gson gson= new Gson();

    /**It loads the offerTile from a json file
     * @param path the path to the json file
     * @return an array of orderTile
     */
    public OfferTile[] loadOfferTile(String path){
        try(InputStream is= getClass().getResourceAsStream(path)) { //prende direttamente il file json
            if (is == null) throw new RuntimeException(path + "not found");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8); //converte lo stream in testo leggibile
            return gson.fromJson(reader,OfferTile[].class); //kegge il json e lo converte direttamente in array (?)
        }catch (IOException e){
            throw new RuntimeException("Error"+path,e);
        }
    }
    /**It loads the orderSlot from a json file
     * @param path is the path to the json file
     * @return an array of orderSlot
     */
    public OrderSlot[] loadOrderSlot(String path){
        try(InputStream is= getClass().getResourceAsStream(path)){
            if (is == null) throw new RuntimeException(path+"not found");
            InputStreamReader reader= new InputStreamReader(is, StandardCharsets.UTF_8);
            return gson.fromJson(reader, OrderSlot[].class);
        }catch (IOException e){
            throw new RuntimeException("Error"+path,e);
        }

    }
}
