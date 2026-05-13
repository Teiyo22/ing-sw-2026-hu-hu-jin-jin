package it.polimi.ingsw;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.ViewFactory;

import java.io.Console;
import java.util.concurrent.TimeUnit;

public class ClientMain {
    public static void main(String[] args) {
        String protocol;
        String address;
        int port; // 28910 | 1099;
        String ui;

        Logger l = Logger.getInstance();
        l.setLevel(LoggerLevel.CLIENT);

        Console console = System.console();

        protocol = console.readLine("Select Network Protocol (tcp | rmi): ").trim();

        if (!protocol.equalsIgnoreCase("rmi") && !protocol.equalsIgnoreCase("tcp")) {
            System.out.println("The network protocol must be either 'tcp' or 'rmi'");
            System.exit(-1);
        }

        String[] input = console.readLine("Enter <ip> <port>: ").trim().split(" ");

        if (input.length != 2) {
            System.out.println("Invalid number of arguments");
            System.exit(-1);
        }

        try {
            address = input[0];
            port = Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            System.out.println("Port must be an integer");
            System.exit(-1);
            return;
        }

        ui = console.readLine("Select UI (tui | gui): ").trim();

        if (!ui.equalsIgnoreCase("tui") && !ui.equalsIgnoreCase("gui")) {
            System.out.println("The UI must be either 'tui' or 'gui'");
            System.exit(-1);
            return;
        }

        ClientController controller = new ClientController();
        View view = ViewFactory.create(ui, controller);
        controller.setView(view);

        boolean success = protocol.equalsIgnoreCase("tcp")
                ? controller.connectTCP(address, port)
                : controller.connectRMI(address, port);

        if (success) {
            try {
                while (!controller.isInit())
                    TimeUnit.MILLISECONDS.sleep(100);

                view.show();
            } catch (InterruptedException ignore) { }
        }

        System.exit(0);
    }
}
