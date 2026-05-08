package it.polimi.ingsw;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

public class ServerMain {

    public static void main(String[] args) {
//        if (args.length < 2 || args.length > 3) {
//            System.out.println("Usage: java -jar <jar name> <tcp address> [tcp port] <rmi port>");
//            System.exit(-1);
//        }

        Logger l = Logger.getInstance();
        l.setLevel(LoggerLevel.DEBUG);

        String address = "127.0.0.1"; // args[0];
        int tcpPort = 28910; // args.length == 3 ? Integer.parseInt(args[1]) : 0;
        int rmiPort = 1099; // Integer.parseInt(args[args.length == 3 ? 2 : 1]);

        if (rmiPort <= 0 || tcpPort < 0) {
            System.out.println("If ports are specified they must be larger than zero!");
            System.exit(-1);
        }
        ServerController controller = ServerController.getInstance();
        controller.startServer(address, tcpPort, rmiPort);
        String line = null;
        do {
            line = System.console().readLine();
        } while (!line.trim().equalsIgnoreCase("stop"));

        controller.stopServer();

        System.exit(0);
    }

}
