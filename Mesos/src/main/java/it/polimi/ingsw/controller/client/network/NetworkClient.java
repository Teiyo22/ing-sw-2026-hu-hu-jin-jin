package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.utils.controller.RequestSerializer;
import it.polimi.ingsw.utils.controller.ResponseDeserializer;
import it.polimi.ingsw.utils.model.CardAdapterFactory;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkClient extends Thread {
    private TCPServerInterface server;
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private final Gson gson;

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
                Response response = gson.fromJson(line, Response.class);
                server.handleMessage(response);
            }
        } catch (SocketException e) {
            Logger.getInstance().print(LoggerLevel.CLIENT, "TCP Socket closed");
        } catch (IOException ignore) {
            Logger.getInstance().print(LoggerLevel.CLIENT, "Failed to read from TCP socket");
        } finally {
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
        String msg = gson.toJson(request);
        try {
            output.write(msg);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            System.out.println("Error while sending message in TCP: " + e.getMessage());
        }

    }

    public void connect(String ip, int tcpPort) throws UnknownHostException, IOException {
        this.socket = new Socket(ip, tcpPort);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        new Thread(this).start();
    }

    public void setServer(TCPServerInterface server) {
        this.server = server;
    }

    public void cleanup() {
        try {
            this.interrupt();

            if (input != null) {
                input.close();
                input = null;
            }
            if (output != null) {
                output.close();
                output = null;
            }

            if (socket != null && !socket.isClosed()) {
                socket.close();
                socket = null;
            }
        } catch (IOException ignore) {
        }
    }
}
