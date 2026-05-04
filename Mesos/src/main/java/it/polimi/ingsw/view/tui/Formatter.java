package it.polimi.ingsw.view.tui;

public class Formatter {
    private static final int width = 140;
    private static final String reset = "\u001B[0m";

    public static String formatSeparatorLine(String title) {
        if (title == null || title.isEmpty())
            return "+" + "=".repeat(width - 2) + "+";

        float multiplier = (width - 4 - title.length()) / 2.0f;
        return "+" + "=".repeat((int) Math.floor(multiplier)) + " " + title + " " + "=".repeat((int) Math.ceil(multiplier)) + "+";
    }

    public static String formatLine(String content) {
        return "| " + content + " ".repeat(width - 4 - content.length()) + " |";
    }

    public static String formatColoredLine(String content, String color) {
        return "| " + color + content + reset + " ".repeat(width - 4 - content.length()) + " |";
    }

    public static String formatColoredWord(String content, String color) {
        content = content.replace("&c", color).replace("&r", reset);
        return "| " + color + content + reset + " ".repeat(width - 4 - content.length()) + " |";
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J"); // Terminal must support ANSI escape sequences for this to work.
        System.out.flush();
    }
}
