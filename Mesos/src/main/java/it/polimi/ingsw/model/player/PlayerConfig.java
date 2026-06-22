package it.polimi.ingsw.model.player;

public enum PlayerConfig {
    TWO(2, "/configs/deck.json", "/configs/offerTrack/two.json", "/configs/orderTile/two.json"),
    THREE(3, "/configs/deck.json", "/configs/offerTrack/three.json", "/configs/orderTile/three.json"),
    FOUR(4, "/configs/deck.json", "/configs/offerTrack/four.json", "/configs/orderTile/four.json") ,
    FIVE(5, "/configs/deck.json", "/configs/offerTrack/five.json", "/configs/orderTile/five.json");

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


    /** Return the respective configuration based on the number of players.
     * Each configuration contains the size and directories for the json files.*/
    public static PlayerConfig getPlayerConfig(int n) {
        for (PlayerConfig c : PlayerConfig.values()) {
            if (c.getNum() == n) {
                return c;
            }
        }
        return null;
    }
}
