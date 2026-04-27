package it.polimi.ingsw.controller.common.messages;

public abstract class Message {
    protected MessageType type;
    protected int clientID;

    public int getClientID() {
        return clientID;
    }

    public MessageType getType() {
        return type;
    }
}
