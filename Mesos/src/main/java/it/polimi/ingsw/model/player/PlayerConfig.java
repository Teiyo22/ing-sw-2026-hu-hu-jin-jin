package it.polimi.ingsw.model.player;

public enum PlayerConfig {
    TWO(2, "tmp.json", "tmp.json", "tmp.json"),
    THREE(3, "tmp.json", "tmp.json", "tmp.json"),
    FOUR(4, "tmp.json", "tmp.json", "tmp.json") ,
    FIVE(5, "tmp.json", "tmp.json", "tmp.json");

    private final int n;
    private final String deckConfigFile;
    private final String offerTrackConfigFile;
    private final String orderTileConfigFile;

    PlayerConfig(int n, String deckConfigFile, String offerTrackConfigFile, String orderTileConfigFile) {
        this.n = n;
        this.deckConfigFile = deckConfigFile;
        this.offerTrackConfigFile = offerTrackConfigFile;
        this.orderTileConfigFile = orderTileConfigFile;
    }

    public int getNum() {
        return n;
    }

    public String getDeckConfigFile() {
        return deckConfigFile;
    }

    public String getOfferTrackConfigFile() {
        return offerTrackConfigFile;
    }

    public String getOrderTileConfigFile() {
        return orderTileConfigFile;
    }
}
