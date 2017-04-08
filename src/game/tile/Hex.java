package game.tile;

import game.settlements.Settlement;

public class Hex {
    private Terrain terrain;
    private Tile owner;
    private Location location;
    private Settlement settlement;

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

    public int getHeight() {
        int height = this.location.getzCoordinate();
        return height;
    }

    public Settlement getSettlement() {
        return this.settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }
}
