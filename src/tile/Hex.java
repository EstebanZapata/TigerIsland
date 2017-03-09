package tile;

public class Hex {
    private Terrain terrain;
    private Side[] sides;
    private int SIDES_ON_HEXAGON = 6;

    Hex(Terrain terrain) {
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
}
