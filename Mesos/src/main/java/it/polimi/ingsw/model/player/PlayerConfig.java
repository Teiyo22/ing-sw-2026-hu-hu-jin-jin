package it.polimi.ingsw.model.player;

public enum PlayerConfig {
    TWO(2, "/configs/deck/two.json", "/configs/offerTrack/two.json", "/configs/orderTile/two.json"),
    THREE(3, "/configs/deck/three.json", "/configs/offerTrack/three.json", "/configs/orderTile/three.json"),
    FOUR(4, "/configs/deck/four.json", "/configs/offerTrack/four.json", "/configs/orderTile/four.json") ,
    FIVE(5, "/configs/deck/five.json", "/configs/offerTrack/five.json", "/configs/orderTile/five.json");

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
