package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class Formatter {
    private static final int width = 160;
    private static final String reset = "\u001B[0m";
    private static final String clear = "\033[H\033[2J";

    public static void clearScreen() {
        System.out.print(clear); // Terminal must support ANSI escape sequences for this to work.
        System.out.flush();
    }

    public static String separatorLine(String title) {
        if (title == null || title.isEmpty())
            return "+" + "=".repeat(width - 2) + "+";

        float multiplier = (width - 4 - title.length()) / 2.0f;
        return "+" + "=".repeat((int) Math.floor(multiplier)) + " " + title + " " + "=".repeat((int) Math.ceil(multiplier)) + "+";
    }

    public static String line(String content) {
        return "| " + content + " ".repeat(width - 4 - content.length()) + " |";
    }

    public static String coloredLine(String content, String color) {
        return "| " + color + content + reset + " ".repeat(width - 4 - content.length()) + " |";
    }

    public static String lobbyID(int content) {
        return line(String.format("Lobby ID:     %3d", content));
    }

    public static String playerCount(int currentCount, int maxCount) {
        return line(String.format("Player Count: %3d/%3d", currentCount, maxCount));
    }

    public static String player(Map.Entry<Player, Integer> player) {
        String identifier = player.getValue() == null ? "Disconnected" : "ID: " + player.getValue();
        String content = String.format("- %-10s (%s)", player.getKey().getName(), identifier);

        return coloredLine(content, player.getKey().getTotem().getColor());
    }

    public static String waitingLobby(Lobby lobby) {
        return line(String.format("Lobby ID: %3d | Size: %3d", lobby.getLobbyID(), lobby.getSize()));
    }

    public static String playerInfo(Player player, boolean isTurn, boolean isMe) {
        String turnMarker = isTurn ? ">" : " ";
        String playerMarker = isMe ? " (You)" : "";

        return coloredLine(String.format("%s %-20s | Food: %-3d | PP: %-3d",
                turnMarker, player.getName() + playerMarker, player.getFood(), player.getPP()), player.getTotem().getColor());
    }
}
