package it.polimi.ingsw.view;

public interface View {
    void show();
    void close();
    void displayError(String message);
    void transitionTo(ScreenType type);
    void update();
}
