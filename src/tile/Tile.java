package tile;

public class Tile {
    private Hex[] hexes;
    private int MAX_HEXES_PER_TILE = 3;

    public static final int VOLCANO_HEX = 0;
    public static final int LEFT_HEX = 1;
    public static final int RIGHT_HEX = 2;

    public Tile(Terrain terrainOne, Terrain terrainTwo) {
        hexes = new Hex[MAX_HEXES_PER_TILE];
        hexes[VOLCANO_HEX] = new Hex(this, Terrain.VOLCANO);
        hexes[LEFT_HEX] = new Hex(this, terrainOne);
        hexes[RIGHT_HEX] = new Hex(this, terrainTwo);
    }

    public Hex getVolcanoHex() {
        return hexes[VOLCANO_HEX];
    }

    public Hex getLeftHexRelativeToVolcano() {
        return hexes[LEFT_HEX];
    }

    public Hex getRightHexRelativeToVolcano() {
        return hexes[RIGHT_HEX];
    }
}
