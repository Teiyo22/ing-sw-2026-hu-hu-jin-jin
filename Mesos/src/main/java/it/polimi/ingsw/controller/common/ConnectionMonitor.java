package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ConnectionMonitor {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final long interval = 3; // [s]
    private final long timeout = 9; // [s]

    private Map<ClientInterface, Long> clientLastSeen;
    private AtomicLong serverLastSeen;

    // =====================================================================
    // Client-side monitoring methods
    // =====================================================================

    public void updateServerLastSeen() {
        serverLastSeen.set(System.currentTimeMillis());
    }

    public void startServerMonitor(ClientController clientController) {
        serverLastSeen = new AtomicLong();
        serverLastSeen.set(System.currentTimeMillis());

        scheduler.scheduleAtFixedRate(() -> {
            long silence = System.currentTimeMillis() - serverLastSeen.get();

            if (silence > timeout * 1000) {
                clientController.disconnect();
                Logger.getInstance().print(LoggerLevel.DEBUG, "Connection Monitor disconnecting server after " + silence / 1000 + "s of silence");
            }
            else
                clientController.getServer().ping(clientController.getID());
        }, 0, interval, TimeUnit.SECONDS);
    }


    // =====================================================================
    // Server-side monitoring methods
    // =====================================================================

    public void registerClient(ClientInterface client) {
        clientLastSeen.put(client, System.currentTimeMillis());
    }

    public void unregisterClient(ClientInterface client) {
        clientLastSeen.remove(client);
    }

    public void updateClientLastSeen(ClientInterface client) {
        clientLastSeen.put(client, System.currentTimeMillis());
    }

    public void startClientMonitor() {
        clientLastSeen = new ConcurrentHashMap<>();

        scheduler.scheduleAtFixedRate(() -> {
            for (ClientInterface client : clientLastSeen.keySet()) {
                long silence = System.currentTimeMillis() - clientLastSeen.get(client);

                if (silence > timeout * 1000) {
                    ServerController.getInstance().disconnect(client.getID());
                    Logger.getInstance().print(LoggerLevel.DEBUG, "Connection Monitor disconnecting client " + client.getID() + " after " + silence / 1000 + "s of silence");
                }
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    // =====================================================================
    // Common monitoring methods
    // =====================================================================

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
