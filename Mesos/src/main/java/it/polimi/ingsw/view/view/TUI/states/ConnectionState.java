package it.polimi.ingsw.view.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;

public class ConnectionState extends ViewState {
    private final String registryName;
    private final String ip;
    private final int port;

    public ConnectionState(ClientController controller, String registryName, String ip, int port) {
        super(controller);
        this.registryName = registryName;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void render() {
        System.out.println("Select connection mode:");
        System.out.println("1. RMI");
        System.out.println("2. TCP");
    }

    @Override
    public void handleInput(String input) {
        switch (input) {
            case "1": super.getController().connectRMI(registryName, ip, port);
            case "2": super.getController().connectTCP(ip, port);
            default: System.out.println("Invalid input: select 1 or 2.");
        }
    }
}
