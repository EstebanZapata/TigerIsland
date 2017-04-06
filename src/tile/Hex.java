package tile;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Hex {
    private Terrain terrain;
    private Tile owner;
    private Location location;
    private Boolean isOccupied;

    public Hex(Tile owner, Terrain terrain) {
        this.owner = owner;
        this.terrain = terrain;
        this.isOccupied = false;
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

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public Boolean isOccupied() {
        return this.isOccupied;
    }

    public int getHeight() {
        int height = this.location.getzCoordinate();
        return height;
    }
}
