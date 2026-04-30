package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.server.ServerController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConnectionMonitor {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final long interval = 5; // [s]
    private final long timeout = 15; // [s]

    private final Map<VirtualClient, Long> lastSeen = new ConcurrentHashMap<>();

    public void registerClient(VirtualClient client) {
        lastSeen.put(client, System.currentTimeMillis());
    }

    public void unregisterClient(VirtualClient client) {
        lastSeen.remove(client);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            for (VirtualClient client : lastSeen.keySet()) {
                try {
                    client.ping();
                    lastSeen.put(client, System.currentTimeMillis());
                } catch (IOException e) {
                    long silence = System.currentTimeMillis() - lastSeen.get(client);

                    if (silence > timeout * 1000) {
                        handleDisconnection(client);
                    }
                }
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    public void handleDisconnection(VirtualClient client) {
        ServerController.getInstance().onClientDisconnected(client);
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}
