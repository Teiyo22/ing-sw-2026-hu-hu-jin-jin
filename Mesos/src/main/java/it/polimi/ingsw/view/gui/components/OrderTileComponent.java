package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.player.Totem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderTileComponent extends JLabel {
    private static final double[] offsetMultipliers =  { 0.253, 0.21, 0.165, 0.095 };

    private final double offsetMultiplier;
    private final Map<Totem, Image> totemIcons;
    private final List<ImageIcon> orderedTotems;

    public OrderTileComponent(ClientController clientController, Map<Totem, Image> totemIcons) {
        this.totemIcons = totemIcons;

        offsetMultiplier = offsetMultipliers[clientController.getCurrLobby().getSize() - 2];
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = (int) (screenSize.height * 0.3);
        int width = (int) (height * (2.0/3.0));

        Image image = new ImageIcon(getClass().getResource("/images/orderTiles/"+clientController.getCurrLobby().getPlayerCount()+".png")).getImage();
        this.setIcon(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        this.setPreferredSize(new Dimension(width, height));

        orderedTotems = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < orderedTotems.size(); i++){
            if(orderedTotems.get(i)!=null){
                orderedTotems.get(i).paintIcon(this, g,
                        (int) (this.getSize().width * 0.345 ),
                        (int) (this.getSize().height * offsetMultiplier + i * this.getSize().height * 0.1725) );
            }
        }
    }

    public void update(ClientController clientController) {
        Board board = clientController.getBoard();
        if (board == null)
            return;

        orderedTotems.clear();
        for (OrderSlot orderSlot : board.getOrderTile()) {
            if (orderSlot.getAssignedPlayer() == null) {
                orderedTotems.add(null);
            } else {
                ImageIcon totemIcon = new ImageIcon(totemIcons.get(orderSlot.getAssignedPlayer().getTotem()).getScaledInstance(
                        Math.max((int) (this.getSize().width * 0.318), 1),
                        Math.max((int) (this.getSize().height * 0.115), 1),
                        Image.SCALE_DEFAULT));
                orderedTotems.add(totemIcon);
            }
        }
    }
}
