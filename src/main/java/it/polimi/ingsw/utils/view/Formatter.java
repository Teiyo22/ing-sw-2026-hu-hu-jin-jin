package it.polimi.ingsw.utils.view;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.leaderboard.LeaderboardEntry;

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
            return "+" + "=".repeat(Math.max(0, width - 2)) + "+";

        float multiplier = (width - 4 - title.length()) / 2.0f;
        return "+" + "=".repeat(Math.max(0, (int) Math.floor(multiplier))) + " " + title + " " + "=".repeat(Math.max(0, (int) Math.ceil(multiplier))) + "+";
    }

    public static String line(String content) {
        return "| " + content + " ".repeat(Math.max(0, width - 4 - content.length())) + " |";
    }

    public static String coloredLine(String content, String color) {
        return "| " + color + content + reset + " ".repeat(Math.max(0, width - 4 - content.length())) + " |";
    }

    public static String lobbyID(int content) {
        return line(String.format("Lobby ID:     %3d", content));
    }

    public static String playerCount(int currentCount, int maxCount) {
        return line(String.format("Player Count: %3d/%3d", currentCount, maxCount));
    }

    public static String player(Map.Entry<Player, Boolean> player) {
        String status = player.getValue() ? "Connected" : "Disconnected";
        String content = String.format("- %-10s (%s)", player.getKey().getName(), status);

        return coloredLine(content, player.getKey().getTotem().getColor());
    }

    public static String waitingLobby(Lobby lobby) {
        return line(String.format("Lobby ID: %3d | Player count = %3d/%3d", lobby.getLobbyID(), lobby.getPlayerCount(), lobby.getSize()));
    }

    public static String playerInfo(Player player, boolean isTurn, boolean isMe) {
        String turnMarker = isTurn ? ">" : " ";
        String playerMarker = isMe ? " (You)" : "";

        return coloredLine(String.format("%s %-20s | Food: %-3d | PP: %-3d",
                turnMarker, player.getName() + playerMarker, player.getFood(), player.getPP()), player.getTotem().getColor());
    }

    public static String playerRank(Player player) {
        Map<Integer, String> ordinalSuffix = Map.of(1, "st", 2, "nd", 3, "rd");
        return line(String.format("%d%s: %-25s [Food: %d | PP: %d]",
            player.getRank(),
            ordinalSuffix.getOrDefault(player.getRank(), "th"),
            player.getName(),
            player.getFood(),
            player.getPP()));
    }

    public static String leaderboardEntry(int rank, LeaderboardEntry entry, int entryID) {
        String formattedString = String.format("%-4d. %-25s | Food: %-3d | PP: %-3d | Date: %s",
            rank,
            entry.getNickname(),
            entry.getFood(),
            entry.getPP(),
            entry.getDate());
        return entry.getId() == entryID ?
            coloredLine(formattedString, "\u001B[42m"):
            line(formattedString);
    }
}
