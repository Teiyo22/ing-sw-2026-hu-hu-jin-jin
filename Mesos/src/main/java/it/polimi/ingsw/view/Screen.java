package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;

public interface Screen {
    void render();
    void showErrors(ErrorMessage errorMsg);
    void showEventResult(EventResultMessage eventResultMessage);
}