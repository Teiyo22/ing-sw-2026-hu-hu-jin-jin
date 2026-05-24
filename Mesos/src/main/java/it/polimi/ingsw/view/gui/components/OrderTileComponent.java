package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.util.ImageCache;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderTileComponent extends JLabel {
    private static final double[] offsetMultipliers = {0.253, 0.21, 0.165, 0.095};

    private final double offsetMultiplier;
    private final Image orderTileImage;
    private final ImageCache imageCache;
    private OrderSlot[] orderTile;

    public OrderTileComponent(ClientController clientController, ImageCache imageCache) {
        this.imageCache = imageCache;
        offsetMultiplier = offsetMultipliers[clientController.getCurrLobby().getSize() - 2];
        orderTile = clientController.getBoard().getOrderTile();
        orderTileImage = ImageCache.loadImage("/images/orderTiles/" + clientController.getCurrLobby().getSize() + ".png");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (screenSize.height * 0.3);
        int width = (int) (height * (2.0 / 3.0));

        this.setIcon(new ImageIcon(orderTileImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension componentSize = this.getSize();
        for (int i = 0; i < orderTile.length && orderTile[i].getAssignedPlayer() != null; i++) {
            g.drawImage(
                imageCache.getImage("/images/totems/" + orderTile[i].getAssignedPlayer().getTotem() + ".png"),
                (int) (componentSize.width * 0.345),
                (int) (componentSize.height * offsetMultiplier + i * componentSize.height * 0.1725),
                (int) (componentSize.width * 0.318),
                (int) (componentSize.height * 0.115),
                this
            );
        }
    }

    public void render(ClientController clientController) {
        Board board = clientController.getBoard();

        if (board != null)
            orderTile = board.getOrderTile();
    }
}
