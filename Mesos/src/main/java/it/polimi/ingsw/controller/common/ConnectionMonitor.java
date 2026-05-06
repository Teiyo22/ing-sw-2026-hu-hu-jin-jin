package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.ClientInterface;

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

    private Map<ClientInterface, Long> clientLastSeen;

    private ClientController clientController;
    private Long serverLastSeen;

    public void startServerMonitor(ClientController clientController) {
        this.clientController = clientController;
        serverLastSeen = System.currentTimeMillis();

        scheduler.scheduleAtFixedRate(() -> {
                long silence = System.currentTimeMillis() - serverLastSeen;

                if (silence > timeout * 1000)
                    this.clientController.disconnect();

        }, 0, interval, TimeUnit.SECONDS);
    }

    public void registerClient(ClientInterface client) {
        clientLastSeen.put(client, System.currentTimeMillis());
    }

    public void unregisterClient(ClientInterface client) {
        clientLastSeen.remove(client);
    }

    public void startClientMonitor() {
        clientLastSeen = new ConcurrentHashMap<>();

        scheduler.scheduleAtFixedRate(() -> {
            for (ClientInterface client : clientLastSeen.keySet()) {
                try {
                    client.ping();
                    clientLastSeen.put(client, System.currentTimeMillis());
                } catch (RemoteException e) {
                    long silence = System.currentTimeMillis() - clientLastSeen.get(client);

                    if (silence > timeout * 1000)
                        disconnectClient(client);

                } catch (IOException e) {
                    disconnectClient(client);
                }
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    public void disconnectClient(ClientInterface client) {
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
