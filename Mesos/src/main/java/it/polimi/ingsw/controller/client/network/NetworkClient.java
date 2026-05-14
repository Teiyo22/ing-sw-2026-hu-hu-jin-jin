package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;

import java.io.*;
import java.net.*;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
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

public class NetworkClient extends Thread {
    private boolean init = false;
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
        } catch (SocketException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "TCP Socket closed");
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to read from TCP socket");
        } catch (JsonParseException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to parse JSON message");
        } catch (Exception e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Unexpected error in TCP thread");
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
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
        if (!init) return;

        try {
            String msg = gson.toJson(request);
            Logger.getInstance().print(LoggerLevel.DEBUG, "Sending message: " + msg);
            output.write(msg);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            System.out.println("Error while sending message in TCP: " + e.getMessage());
        } catch (Exception e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Unexpected error in TCP thread");
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
        }

    }

    public void connect(String ip, int tcpPort) throws IOException, IllegalArgumentException {
        InetSocketAddress endpoint = new InetSocketAddress(ip, tcpPort);
        this.socket = new Socket();
        this.socket.connect(endpoint, 2000);

        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        this.init = true;
        new Thread(this).start();
    }

    public void setServer(TCPServerInterface server) {
        this.server = server;
    }

    public void cleanup() {
        init = false;

        try {
            this.interrupt();

            if (socket != null && !socket.isClosed()) {
                socket.close();
                socket = null;
            }

            if (input != null) {
                input.close();
                input = null;
            }
            if (output != null) {
                output.close();
                output = null;
            }

        } catch (IOException ignore) {
        }
    }
}
