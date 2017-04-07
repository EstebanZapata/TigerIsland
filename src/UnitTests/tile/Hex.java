package UnitTests.tile;

public class Hex {
    private Terrain terrain;
    private Tile owner;
    private Location location;

    public Hex(Tile owner, Terrain terrain) {
        this.owner = owner;
        this.terrain = terrain;

    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public Tile getOwner() {
        return this.owner;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }
}
