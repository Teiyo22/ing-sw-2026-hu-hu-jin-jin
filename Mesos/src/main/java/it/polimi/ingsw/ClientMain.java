package it.polimi.ingsw;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.ViewFactory;

import java.util.concurrent.TimeUnit;

public class ClientMain {
    public static void main(String[] args) {
//        if (args.length < 2 || args.length > 3) {
//            System.out.println("Usage: java -jar <jar name> <tcp address> [tcp port] <rmi port>");
//            System.exit(-1);
//        }

        Logger l = Logger.getInstance();
        l.setLevel(LoggerLevel.OFF);

        String address = "127.0.0.1"; // args[0];
        int tcpPort = 28910; // args.length == 3 ? Integer.parseInt(args[1]) : 0;
        int rmiPort = 1099; // Integer.parseInt(args[args.length == 3 ? 2 : 1]);

//        if (rmiPort <= 0 || tcpPort < 0) {
//            System.out.println("If ports are specified they must be larger than zero!");
//            System.exit(-1);
//        }

        ClientController controller = new ClientController();
        controller.connectTCP(address, tcpPort);

        View view = ViewFactory.create("tui", controller);
        controller.setView(view);

        while (!controller.isInit()) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }

        if (controller.isInit()) {
            view.show();
        }

        System.exit(0);
    }
}
