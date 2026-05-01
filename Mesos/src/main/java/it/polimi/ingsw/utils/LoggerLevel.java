package it.polimi.ingsw.utils;

public enum LoggerLevel {
    OFF(8, "\033[0m"),
    ERROR(7, "\u001B[41m\033[1;93m"),
    WARN(6, "\033[1;33m"),
    SERVR(4, "\033[0;33m"),
    MODEL(2, "\033[0;37m"),
    DEBUG(1, "\033[0;37m"),
    ALL(0, "\033[0m");
    private final int level;
    private final String color;

    LoggerLevel(int status, String color) {
        this.level = status;
        this.color = color;
    }

    static String reset() {
        return "\033[0m";
    }

    public int status() {
        return this.level;
    }

    public String color() {
        return this.color;
    }

    @Override
    public String toString() {
        return switch (this) {
            case ALL -> "[ALL--] ";
            case DEBUG -> "[DEBUG] ";
            case MODEL -> "[MODEL] ";
            case SERVR -> "[SERVR] ";
            case WARN -> "[WARN-] ";
            case ERROR -> "[ERROR] ";
            default -> null;
        };
    }

}