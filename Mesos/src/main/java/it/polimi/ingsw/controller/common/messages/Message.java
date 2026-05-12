package it.polimi.ingsw.controller.common.messages;

public abstract class Message {
    protected MessageType type;

    public MessageType getType() {
        return type;
    }
}
