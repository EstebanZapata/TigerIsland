package tile;

public class Hex {
    private Terrain terrain;



    Hex(Terrain terrain) {
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }
}
