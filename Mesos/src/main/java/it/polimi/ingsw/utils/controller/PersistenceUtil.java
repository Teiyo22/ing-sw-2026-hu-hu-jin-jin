package it.polimi.ingsw.utils.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.utils.model.CardAdapterFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PersistenceUtil {
    private static final Path filePath = Paths.get("saves", "data.json");

    private final ScheduledExecutorService scheduler;
    private final Gson gson;

    public PersistenceUtil() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        gson = new GsonBuilder()
            .registerTypeAdapter(AbstractCard.class, new CardAdapterFactory<>().create(AbstractCard.class))
            .registerTypeAdapter(AbstractCharacter.class, new CardAdapterFactory<AbstractCharacter>().create(AbstractCharacter.class))
            .registerTypeAdapter(AbstractBuilding.class, new CardAdapterFactory<AbstractBuilding>().create(AbstractBuilding.class))
            .registerTypeAdapter(AbstractEvent.class, new CardAdapterFactory<AbstractEvent>().create(AbstractEvent.class))
            .setPrettyPrinting()
            .create();
    }

    //=============================================================================
    // Persistence
    //=============================================================================

    public void start(Map<Integer, LobbyController> lobbies) {
        scheduler.scheduleAtFixedRate(() -> {
            Map<Integer, Game> data = lobbies.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().getModelSnapshot()))
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Logger.getInstance().print(LoggerLevel.SERVER, "Persistence: saving " + data.size() + " lobbies" );

            if (filePath.getParent() != null)
                filePath.getParent().toFile().mkdirs();

            Path tempPath = filePath.resolveSibling(filePath.getFileName() + ".tmp");

            try {
                Type type = new TypeToken<Map<Integer, Game>>(){}.getType();
                String json  = gson.toJson(data, type);

                Files.writeString(tempPath, json, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.SYNC);

                Files.move(tempPath, filePath, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);

                Logger.getInstance().print(LoggerLevel.SERVER, "Persistence: successfully saved lobbies" );
            } catch (Exception e) {
                Logger.getInstance().print(LoggerLevel.SERVER, "Persistence saving: " + e.getMessage());
            }

        }, 10, 10, TimeUnit.SECONDS);
    }

    public Map<Integer, LobbyController> loadSaves() {
        Map<Integer, LobbyController> lobbies = new ConcurrentHashMap<>();

        if (filePath.getParent() != null)
            filePath.getParent().toFile().mkdirs();

        try (FileReader reader = new FileReader(filePath.toFile())) {
            Type type = new TypeToken<Map<Integer, Game>>() {}.getType();
            Map<Integer, Game> data = gson.fromJson(reader, type);

            if (data != null) {
                Logger.getInstance().print(LoggerLevel.SERVER, "Persistence: loading " + data.size() + " lobbies");
                for (Map.Entry<Integer, Game> entry : data.entrySet()) {
                    try {
                        entry.getValue().build();
                        lobbies.put(entry.getKey(), new LobbyController(entry.getKey(), entry.getValue()));
                    } catch (Exception e) {
                        Logger.getInstance().print(LoggerLevel.ERROR, "Persistence loading: " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            Logger.getInstance().print(LoggerLevel.SERVER, "Persistence loading: " + e.getMessage());
        }

        return lobbies;
    }

    public void stop() {
        scheduler.shutdown();

        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
