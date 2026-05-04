package it.polimi.ingsw.model.player;

public enum Totem {
    RED("\u001B[41m"),
    BLUE("\u001B[34m"),
    WHITE("\u001B[37m"),
    BLACK("\u001B[30m"),
    YELLOW("\u001B[33m");

    String ansiColor;

    Totem(String ansiColor) {
        this.ansiColor = ansiColor;
    }

    public String getColor() {
        return ansiColor;
    }
}
