package it.polimi.ingsw;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.io.Console;

public class ServerMain {

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int tcpPort;
        int rmiPort;

        Logger l = Logger.getInstance();
        l.setLevel(LoggerLevel.DEBUG);

        Console console = System.console();

        String[] input = console.readLine("Enter <ip> <tcpPort> <rmiPort>: ").trim().split(" ");

        if (input.length != 3) {
            System.out.println("Invalid number of arguments");
            System.exit(-1);
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
