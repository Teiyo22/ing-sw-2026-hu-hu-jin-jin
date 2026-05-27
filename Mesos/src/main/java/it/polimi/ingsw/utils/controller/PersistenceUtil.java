package it.polimi.ingsw.utils.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.utils.model.CardAdapterFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PersistenceUtil {
    private final ScheduledExecutorService scheduler;
    private final Gson gson;

    public PersistenceUtil() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        gson = new GsonBuilder()
            .registerTypeAdapter(AbstractCard.class, new CardAdapterFactory<>().create(AbstractCard.class))
            .setPrettyPrinting()
            .create();
    }

    //=============================================================================
    // Persistence
    //=============================================================================

    public void start(Map<Integer, LobbyController> lobbies) {
        scheduler.scheduleAtFixedRate(() -> {
            Logger.getInstance().print(LoggerLevel.SERVER, "Persistence: Saving game data");
            Map<Integer, Game> data = lobbies.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().getModelSnapshot()))
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Path filePath = Paths.get("saves", "data.json");
            if (filePath.getParent() != null)
                filePath.getParent().toFile().mkdirs();

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                Type type = new TypeToken<Map<Integer, Game>>(){}.getType();
                gson.toJson(data, type, writer);
                writer.flush();
            } catch (Exception e) {
                Logger.getInstance().print(LoggerLevel.ERROR, "Persistence error: " + e.getMessage());
            }

        }, 10, 10, TimeUnit.SECONDS);
    }

//    public Map<Integer, LobbyController> loadSaves() {
//        File file = new File("saves/lobbies.json");
//        if (!file.exists()) return;
//
//        try {
//            FileReader reader = new FileReader(file);
//            Type type = new TypeToken<Map<Integer, Game>>() {
//            }.getType();
//            Map<Integer, Game> savedMap = gson.fromJson(reader, type);
//            reader.close();
//
//            if (savedMap == null || savedMap.isEmpty()) return;
//
//            int maxId = 0;
//            for (Map.Entry<Integer, Game> entry : savedMap.entrySet()) {
//                Game game = entry.getValue();
//                int id = entry.getKey();
//
//                LobbyController lc = new LobbyController(id, game.getPlayerConfig().getNum());
//                lc.setModel(game);
//                lc.setState(new LobbyPausedState(lc));
//
//                savedLobbies.put(id, lc);
//                if (id > maxId) maxId = id;
//            }
//
//            nextLobbyID.set(maxId + 1);
//
//        } catch (IOException e) {
//            System.err.println("[Boot] Errore caricamento: " + e.getMessage());
//        }
//    }

    public void stop() {
        scheduler.shutdown();

        try {
            if (!scheduler.awaitTermination(3, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
