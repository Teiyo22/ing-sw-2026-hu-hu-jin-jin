package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.utils.controller.*;
import it.polimi.ingsw.utils.model.CardAdapterFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler extends Thread {
    private TCPClientInterface tcpClientInterface;
    private Socket socket;
    private Gson gson;
    private BufferedReader input;
    private BufferedWriter output;
    private ReentrantLock lock = new ReentrantLock();

    public ClientHandler(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));


        this.gson = new GsonBuilder()
                .registerTypeAdapter(AbstractCharacter.class, new CardAdapterFactory<AbstractCharacter>().create(AbstractCharacter.class))
                .registerTypeAdapter(AbstractBuilding.class, new CardAdapterFactory<AbstractBuilding>().create(AbstractBuilding.class))
                .registerTypeAdapter(AbstractEvent.class, new CardAdapterFactory<AbstractEvent>().create(AbstractEvent.class))
                .registerTypeAdapter(Request.class, new RequestDeserializer())
                .registerTypeAdapter(Response.class, new ResponseSerializer())
                .registerTypeAdapter(ModelStateInfo.class, new StateInfoSerializer())
                .registerTypeAdapter(PlayerAction.class, new PlayerActionDeserializer())
                .create();
    }

    @Override
    public void run() {
        String line;

        try {
            while ((line = input.readLine()) != null) {
                Logger.getInstance().print(LoggerLevel.DEBUG, "Received message from client " + tcpClientInterface.getID() +": " + line);
                Request request = gson.fromJson(line, Request.class);
                tcpClientInterface.handleMessage(request);
            }
        } catch (SocketException | JsonParseException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
            ServerController.getInstance().disconnectClient(tcpClientInterface);
        }
    }

    public void sendMessage(Response response) {
        lock.lock();
        try {
            String message = gson.toJson(response);
            Logger.getInstance().print(LoggerLevel.DEBUG, "Sending message to client " + tcpClientInterface.getID() +": " + message);
            output.write(message);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, e.getMessage());
            ServerController.getInstance().disconnectClient(tcpClientInterface);
        } finally {
            lock.unlock();
        }
    }

    public void setClientTCPInterface(TCPClientInterface tcpClientInterface) {
        this.tcpClientInterface = tcpClientInterface;
    }

    public void cleanup() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                Logger.getInstance().print(LoggerLevel.SERVER, "TCP Client Socket successfully closed");
            }
        } catch (IOException ignore) {
        }
    }
}