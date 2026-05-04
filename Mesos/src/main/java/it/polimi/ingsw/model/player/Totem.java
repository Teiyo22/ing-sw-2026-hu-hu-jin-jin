package it.polimi.ingsw.model.player;

import java.io.Serializable;

public enum Totem implements Serializable {
    RED("\u001B[1m\u001B[31m"),
    BLUE("\u001B[1m\u001B[34m"),
    WHITE("\u001B[1m\u001B[37m"),
    BLACK("\u001B[1m\u001B[30m"),
    YELLOW("\u001B[1m\u001B[33m");

    final String ansiColor;

    Totem(String ansiColor) {
        this.ansiColor = ansiColor;
    }

    public String getColor() {
        return ansiColor;
    }
}
