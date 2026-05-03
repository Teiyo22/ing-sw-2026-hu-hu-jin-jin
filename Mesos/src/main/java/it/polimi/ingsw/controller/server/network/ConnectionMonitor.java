package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.server.ServerController;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConnectionMonitor {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final long interval = 5; // [s]
    private final long timeout = 15; // [s]

    private final Map<ClientInterface, Long> lastSeen = new ConcurrentHashMap<>();

    public void registerClient(ClientInterface client) {
        lastSeen.put(client, System.currentTimeMillis());
    }

    public void unregisterClient(ClientInterface client) {
        lastSeen.remove(client);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            for (ClientInterface client : lastSeen.keySet()) {
                try {
                    client.ping();
                    lastSeen.put(client, System.currentTimeMillis());
                } catch (RemoteException e) {
                    long silence = System.currentTimeMillis() - lastSeen.get(client);

                    if (silence > timeout * 1000)
                        handleDisconnection(client);

                } catch (IOException e) {
                    handleDisconnection(client);
                }
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    public void handleDisconnection(ClientInterface client) {
        ServerController.getInstance().disconnectClient(client);
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
