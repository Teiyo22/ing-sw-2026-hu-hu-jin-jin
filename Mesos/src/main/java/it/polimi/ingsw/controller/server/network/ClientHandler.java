package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.utils.controller.RequestDeserializer;
import it.polimi.ingsw.utils.controller.ResponseSerializer;
import it.polimi.ingsw.utils.model.CardTypeAdapter;
import it.polimi.ingsw.utils.model.CardTypeAdapterFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread {
    private TCPClientInterface tcpClientInterface;
    private Socket socket;
    private Gson gson;
    private BufferedReader input;
    private BufferedWriter output;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        CardTypeAdapter buildingAdapter = CardTypeAdapterFactory.create(AbstractBuilding.class);
        CardTypeAdapter characterAdapter = CardTypeAdapterFactory.create(AbstractCharacter.class);
        CardTypeAdapter eventAdapter = CardTypeAdapterFactory.create(AbstractEvent.class);

        this.gson = new GsonBuilder()
                .registerTypeAdapter(AbstractBuilding.class, buildingAdapter)
                .registerTypeAdapter(AbstractCharacter.class, characterAdapter)
                .registerTypeAdapter(AbstractEvent.class, eventAdapter)
                .registerTypeAdapter(Request.class, new RequestDeserializer())
                .registerTypeAdapter(Response.class, new ResponseSerializer())
                .create();
    }

    @Override
    public void run() {
        String line;

        try {
            while ((line = input.readLine()) != null) {
                Request request = gson.fromJson(line, Request.class);
                tcpClientInterface.handleMessage(request);
            }
            ServerController.getInstance().disconnectClient(tcpClientInterface);
        } catch (SocketException ignore) {

        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.SERVER, "Disconnected from TCP client: " + tcpClientInterface.getID());
            ServerController.getInstance().disconnectClient(tcpClientInterface);
        }
    }

    public void sendMessage(Response response) {
        try {
            String message = gson.toJson(response);
            output.write(message);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            cleanup();
        }
    }

    public void setClientTCPInterface(TCPClientInterface tcpClientInterface){
        this.tcpClientInterface = tcpClientInterface;
    }

    public void cleanup() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                Logger.getInstance().print(LoggerLevel.SERVER, "TCP Client Socket successfully closed");
            }
        } catch (IOException ignore) { }
    }
}