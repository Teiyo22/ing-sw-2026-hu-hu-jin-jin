package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;

public interface Screen {
    void render();
    void showErrors(ErrorMessage errorMsg);
}