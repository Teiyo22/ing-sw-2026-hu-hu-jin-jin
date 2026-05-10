package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.view.gui.components.CardComponent;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.components.OfferTileComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GamePlayScreen extends GUIScreen implements MouseListener{
    enum GamePhase {
        CARD_PICK,
        OFFER_PICK
    }
    private GamePhase gamePhase;
    private final JButton confirmButton;
    private final CardPicksListener topListener;
    private final CardPicksListener bottomListener;
    private final OfferPickListener offerListener;

    public GamePlayScreen(JFrame frame, ClientController controller) {
        super(frame, controller);
        confirmButton = new JButton("Confirm");
        topListener = new CardPicksListener();
        bottomListener = new CardPicksListener();
        OfferTile[] offerTrack = controller.getCurrLobby().getBoard().getOfferTrack();
        offerListener = new OfferPickListener(IntStream.range(0, offerTrack.length)
                .boxed().collect(Collectors.toMap(i -> offerTrack[i], i -> i)));
    }

    @Override
    public void render() {
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());

        top.setPreferredSize(new Dimension(1920, 200));
        top.setBackground(new Color(0xEE3F2A));

        bottom.setPreferredSize(new Dimension(1920, 200));
        bottom.setBackground(new Color(0xEE3F2A));

        center.setBackground(new Color(0xEE3F2A));

        renderTopRow(top);
        renderBottomRow(bottom);
        renderOfferTrack(center);

        renderConfirmButton(center);

        enableComponents();

        frame.add(top, BorderLayout.NORTH);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.add(center, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gamePhase == GamePhase.CARD_PICK) {
            try {
                clientController.getServer().requestCards(clientController.getID(), clientController.getCurrLobby().getLobbyID(),
                        topListener.getPicks(), bottomListener.getPicks());
            } catch (RemoteException ex) {
                showError(ex.getMessage());
            }
        } else if(gamePhase == GamePhase.OFFER_PICK) {
            try {
                clientController.getServer().requestOffer(clientController.getID(), clientController.getCurrLobby().getLobbyID(),
                        offerListener.getSelectedOfferIndex());
            } catch (RemoteException ex) {
                showError(ex.getMessage());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(confirmButton.isEnabled()) {
            confirmButton.setBorder(BorderFactory.createRaisedBevelBorder());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        confirmButton.setBorder(null);
    }



    public void renderTopRow(JPanel panel) {
        renderRow(clientController.getCurrLobby().getBoard().getTopRow(), panel, topListener);
    }

    public void renderBottomRow(JPanel panel) {
        renderRow(clientController.getCurrLobby().getBoard().getBottomRow(), panel, bottomListener);
    }

    public void renderRow(Row row, JPanel panel, CardPicksListener listener) {
        List<AbstractCard> pickableCards = new ArrayList<>();
        pickableCards.addAll(row.getCharacterCards());
        pickableCards.addAll(row.getBuildingCards());

        List<AbstractCard> eventCards = new ArrayList<>();
        eventCards.addAll(row.getEventCards());
        eventCards.addAll(row.getSustenanceEventCards());

        for (AbstractCard c : pickableCards) {
            renderCard(c, panel, listener);
        }
        for (AbstractCard c : eventCards) {
            renderCard(c, panel, null);
        }
    }

    public void renderCard(AbstractCard c, JPanel panel, CardPicksListener listener) {
        CardComponent cardComponent = new CardComponent(c, listener);
        cardComponent.renderFront();
        panel.add(cardComponent);
    }


    public void renderOfferTrack(JPanel panel) {
        for(OfferTile o : clientController.getCurrLobby().getBoard().getOfferTrack()){
            OfferTileComponent offerTileComponent = new OfferTileComponent(o, offerListener);
            offerTileComponent.render();
            panel.add(offerTileComponent, BorderLayout.CENTER);
        }
    }

    public void renderConfirmButton(JPanel panel) {
        confirmButton.setBackground(new Color(0xFFF3D3));
        confirmButton.setHorizontalAlignment(SwingConstants.EAST);
        panel.add(confirmButton, BorderLayout.SOUTH);
    }



    private void enableComponents() {
        if(clientController.getCurrLobby().getTurnState().canPickCard() && matchTurn()){
            topListener.setTotalPicks(clientController.getCurrLobby().getBoard().getTopRow().getPickableCardCount());
            topListener.enable();
            bottomListener.setTotalPicks(clientController.getCurrLobby().getBoard().getBottomRow().getPickableCardCount());
            bottomListener.enable();

            offerListener.disable();

            gamePhase = GamePhase.CARD_PICK;
            confirmButton.setEnabled(true);
        } else if (clientController.getCurrLobby().getTurnState().canPickOffer() && matchTurn()){
            topListener.disable();
            bottomListener.disable();

            offerListener.enable();

            gamePhase = GamePhase.OFFER_PICK;
            confirmButton.setEnabled(true);
        } else {
            topListener.disable();
            bottomListener.disable();
            offerListener.disable();
            confirmButton.setEnabled(false);
        }
    }

    private boolean matchTurn(){
        return clientController.getCurrLobby().getCurrPlayer().equals(clientController.getCurrLobby().getPlayer(clientController.getID()));
    }



    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
