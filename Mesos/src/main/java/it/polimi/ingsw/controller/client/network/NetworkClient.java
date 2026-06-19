package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;

import java.io.*;
import java.net.*;

import it.polimi.ingsw.model.gameState.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.utils.controller.*;
import it.polimi.ingsw.utils.model.CardAdapterFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantLock;

public class NetworkClient extends Thread {
    private TCPServerInterface server;
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private final Gson gson;

    private final ReentrantLock lock = new ReentrantLock();

    public NetworkClient() {
        this.server = null;
        this.socket = null;
        this.input = null;
        this.output = null;


        this.gson = new GsonBuilder()
                .registerTypeAdapter(AbstractCharacter.class, new CardAdapterFactory<AbstractCharacter>().create(AbstractCharacter.class))
                .registerTypeAdapter(AbstractBuilding.class, new CardAdapterFactory<AbstractBuilding>().create(AbstractBuilding.class))
                .registerTypeAdapter(AbstractEvent.class, new CardAdapterFactory<AbstractEvent>().create(AbstractEvent.class))
                .registerTypeAdapter(Request.class, new RequestSerializer())
                .registerTypeAdapter(Response.class, new ResponseDeserializer())
                .registerTypeAdapter(ModelStateInfo.class, new StateInfoDeserializer())
                .registerTypeAdapter(PlayerAction.class, new PlayerActionSerializer())
                .create();
    }


    /**
     * The NetworkClient runs and listens to messages from the server.
     * When a message is received it gets passed to the ServerTCPInterface to handle it.
     *
     */
    @Override
    public void run() {
        String line;
        try {
            while (!Thread.currentThread().isInterrupted() && (line = input.readLine()) != null) {
                Logger.getInstance().print(LoggerLevel.DEBUG, "Received message: " + line);
                Response response = gson.fromJson(line, Response.class);
                server.handleMessage(response);
            }
            server.getClientController().disconnect();
        } catch (SocketException | JsonParseException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
            server.getClientController().disconnect();
        }
    }


    /**
     * Method to serialize and send messages to the server.
     *
     * @param request The message to serialize and send.
     *
     */
    public void sendMessage(Request request) {
        lock.lock();
        try {
            String msg = gson.toJson(request);
            Logger.getInstance().print(LoggerLevel.DEBUG, "Sending message: " + msg);
            output.write(msg);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Error while sending message in TCP: " + e.getMessage());
            server.getClientController().disconnect();
        } catch (Exception e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Unexpected error in TCP thread: " + e.getMessage());
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
        } finally {
            lock.unlock();
        }

    }

    /**Connects to the server by creating and connecting a socket.
     * It then starts a separate thread to listen for server messages.*/
    public void connect(String ip, int tcpPort) throws IOException, IllegalArgumentException {
        InetSocketAddress endpoint = new InetSocketAddress(ip, tcpPort);
        this.socket = new Socket();
        this.socket.connect(endpoint, 2000);

        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        new Thread(this).start();

    }

    public void setServer(TCPServerInterface server) {
        this.server = server;
    }

    public void cleanup() {
        try {
            if (output != null) {
                output.flush();
                output.close();
                output = null;
            }
        } catch (IOException ignore) {}

        try {
            if (input != null) {
                input.close();
                input = null;
            }
        } catch (IOException ignore) {}

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                socket = null;
            }
        } catch (IOException ignore) {}

        this.interrupt();
    }
}
