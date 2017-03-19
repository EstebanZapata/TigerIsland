package tile;

public class Hex {
    private Terrain terrain;
    private Tile owner;
    private Side[] sides;
    private int SIDES_ON_HEXAGON = 6;

    public Hex(Tile owner, Terrain terrain) {
        this.owner = owner;
        this.terrain = terrain;
        this.sides = new Side[SIDES_ON_HEXAGON];

        initializeSides();
    }

    private void initializeSides() {
        for (int i = 0; i < SIDES_ON_HEXAGON; i++) {
            sides[i] = new Side(this);
        }
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public Side[] getSides() {
        return this.sides;
    }

    public Tile getOwner() {
        return this.owner;
    }
}
