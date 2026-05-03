package it.polimi.ingsw.view;

public interface Screen {
    void render();
    void handleInput(String input);
    void onEnter();
    void onExit();
}