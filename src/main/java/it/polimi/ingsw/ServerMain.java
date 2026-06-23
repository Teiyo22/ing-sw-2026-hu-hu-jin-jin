package it.polimi.ingsw;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

import java.io.Console;

public class ServerMain {

    public static void main(String[] args) {
        String address;
        int tcpPort;
        int rmiPort;

        Logger l = Logger.getInstance();
        l.setLevel(LoggerLevel.SERVER);

        Console console = System.console();

        System.out.print("\033[H\033[2J");
        String[] input = console.readLine("Enter <ip> <tcpPort> <rmiPort>: ").trim().split(" ");

        if (input.length != 3) {
            System.out.println("Invalid number of arguments");
            input = new String[3];
            input[0] = "127.0.0.1";
            input[1] = "28910";
            input[2] = "1099";
            System.out.println("Using default values: " + input[0] + " " + input[1] + " " + input[2]);
        }

        try {
            address = input[0];
            tcpPort = Integer.parseInt(input[1]);
            rmiPort = Integer.parseInt(input[2]);
        } catch (NumberFormatException e) {
            System.out.println("Ports must be an integers");
            System.exit(-1);
            return;
        }

        ServerController controller = ServerController.getInstance();

        System.setProperty("java.rmi.server.hostname", address);
        if (controller.startServer(address, tcpPort, rmiPort)) {
            String line;

            do {
                line = System.console().readLine();
            } while (!line.trim().equalsIgnoreCase("stop"));

            controller.stopServer();
        }

        System.exit(0);
    }

}
