package it.polimi.ingsw.view;

public interface View {
    void start();
    void close();
    void displayError(String message);
    void transitionTo(ScreenType type);
    void update();
}
