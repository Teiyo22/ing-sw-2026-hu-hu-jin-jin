package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.player.Totem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderTileComponent extends JLabel {
    private static final int[] firstSlotYPerPlayerNum =  { 74, 61, 47, 28 };  //pixel distance from order tile's top in order of number of players
    private final int firstSlotY;

    private final Map<Totem, ImageIcon> totemIcons;
    private final List<ImageIcon> orderedTotems;

    public OrderTileComponent(ClientController clientController, Map<Totem, ImageIcon> totemIcons) {
        this.totemIcons = totemIcons;

        firstSlotY = firstSlotYPerPlayerNum[clientController.getCurrLobby().getSize()-2];

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = (int) (screenSize.height * 0.35);
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
                orderedTotems.get(i).paintIcon(this, g, 72, firstSlotY + (i*52));
            }
        }
    }

    public void update(ClientController clientController) {
        orderedTotems.clear();

        OrderSlot[] orderTile = clientController.getCurrLobby().getBoard().getOrderTile();

        for (OrderSlot orderSlot : orderTile) {
            if (orderSlot.getAssignedPlayer() == null) {
                orderedTotems.add(null);
            } else {
                orderedTotems.add(totemIcons.get(orderSlot.getAssignedPlayer().getTotem()));
            }
        }
    }
}
