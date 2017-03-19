package tile;

public class Tile {
    private Hex[] hexes;
    private int MAX_HEXES_PER_TILE = 3;
    private Location locationOfVolcano;
    private int orientationOfTile;


    public Tile(Terrain terrainOne, Terrain terrainTwo) {
        hexes = new Hex[MAX_HEXES_PER_TILE];
        hexes[0] = new Hex(this, Terrain.VOLCANO);
        hexes[1] = new Hex(this, terrainOne);
        hexes[2] = new Hex(this, terrainTwo);
    }


    public Hex[] getHexes() {
        return this.hexes;
    }
}
