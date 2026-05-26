package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;

public interface View {
    void start();
    void close();
    void displayError(ErrorMessage errorMsg);
    void displayEventResult(EventResultMessage eventResultMessage);
    void transitionTo(ScreenType type);
    void notifyChange();
}
