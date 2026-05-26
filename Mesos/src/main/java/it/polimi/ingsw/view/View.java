package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;

public interface View {
    void start();
    void close();
    void displayError(ErrorMessage errorMsg);
    void transitionTo(ScreenType type);
    void notifyChange();
}
