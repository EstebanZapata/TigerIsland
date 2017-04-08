package tile;

import game.settlements.Settlement;
import game.*;

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
        int height = this.location.getHeight();
        return height;
    }

    public Settlement getSettlement() {
        return this.settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public boolean checkSettlementConditions() {
        if (this.terrain == Terrain.VOLCANO) {
            return false;
        }

        if (this.getHeight() != Settings.START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT) {
            return false;
        }

        if (this.settlement != null) {
            return false;
        }

        return true;
    }

    public boolean checkExpansionConditions(Terrain terrainType) {
        if (this.terrain != terrainType) {
            return false;
        }

        if (this.settlement != null) {
            return false;
        }

        return true;
    }

    public boolean checkPlaygroundConditions() {
        if (this.terrain == Terrain.VOLCANO) {
            return false;
        }

        if (this.getHeight() < 3) {
            return false;
        }

        if (this.settlement != null) {
            return false;
        }

        return true;
    }

    public boolean checkSanctuaryConditons() {
        if (this.terrain == Terrain.VOLCANO) {
            return false;
        }

        if (this.settlement != null) {
            return false;
        }

        return true;
    }
}
