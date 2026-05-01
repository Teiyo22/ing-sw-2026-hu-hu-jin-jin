package it.polimi.ingsw.utils;

import java.io.PrintStream;

public class Logger {

    static private Logger instance = null;

    private final Object lock = new Object();
    private PrintStream output = System.out;
    private LoggerLevel level = LoggerLevel.MODEL;

    static public Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    static public void reset() {
        instance = null;
    }

    public void setLevel(LoggerLevel level) {
        this.level = level;
    }

    /**
     * Print to the set {@link PrintStream}.
     * @param level {@link LoggerLevel} Message level.
     * @param message Message to be written.
     */
    public void print(LoggerLevel level, String message) {
        if (level.status() < this.level.status()) return;
        synchronized (lock) {
            output.println(level.color() + level + message + LoggerLevel.reset());
        }
    }

}