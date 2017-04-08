package tile;

public class FirstTile extends Tile {
    private static final int HEXES_ON_STARTING_TILE = 5;
    private static final int VOLCANO_HEX = 0;
    private static final int JUNGLE_HEX = 1;
    private static final int LAKE_HEX = 2;
    private static final int GRASSLANDS_HEX = 3;
    private static final int ROCKY_HEX = 4;

    public FirstTile() {
        hexes = new Hex[HEXES_ON_STARTING_TILE];
        hexes[VOLCANO_HEX] = new Hex(this, Terrain.VOLCANO);
        hexes[JUNGLE_HEX] = new Hex(this, Terrain.JUNGLE);
        hexes[LAKE_HEX] = new Hex(this, Terrain.LAKE);
        hexes[GRASSLANDS_HEX] = new Hex(this, Terrain.GRASSLANDS);
        hexes[ROCKY_HEX] = new Hex(this, Terrain.ROCKY);
    }

    public Hex getJungleHex() {
        return hexes[JUNGLE_HEX];
    }

    public Hex getLakeHex() {
        return hexes[LAKE_HEX];
    }

    public Hex getGrasslandsHex() {
        return hexes[GRASSLANDS_HEX];
    }

    public Hex getRockyHex() {
        return hexes[ROCKY_HEX];
    }

    public Hex getVolcanoHex() {
        return hexes[VOLCANO_HEX];
    }

}
