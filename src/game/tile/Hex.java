package game.tile;

import game.settlements.Settlement;
import game.*;
import game.settlements.exceptions.HexDoesNotMeetConditionsException;
import game.settlements.exceptions.SettlementAlreadyExistsOnHexException;
import game.settlements.exceptions.SettlementCannotBeBuiltOnVolcanoException;
import game.settlements.exceptions.SettlementHeightRequirementException;

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

    public void checkFoundingConditions() throws
            SettlementCannotBeBuiltOnVolcanoException,
            SettlementHeightRequirementException,
            SettlementAlreadyExistsOnHexException
    {
        if (this.terrain == Terrain.VOLCANO) {
            String errorMessage = "A settlement cannot be built on a volcano.";
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        if (this.getHeight() != Settings.START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT) {
            String errorMessage = "Hex does not meet height requirement to found a settlement.";
            throw new SettlementHeightRequirementException(errorMessage);
        }

        if (this.settlement != null) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }
    }

    public void checkExpansionConditions(Terrain terrainType) throws
            HexDoesNotMeetConditionsException,
            SettlementAlreadyExistsOnHexException
    {
        if (this.terrain != terrainType) {
            String errorMessage = "Hex does not match terrain type passed in.";
            throw new HexDoesNotMeetConditionsException(errorMessage);
        }

        if (this.settlement != null) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }
    }

    public void checkPlaygroundConditions() throws
            SettlementCannotBeBuiltOnVolcanoException,
            SettlementHeightRequirementException,
            SettlementAlreadyExistsOnHexException
    {
        if (this.terrain == Terrain.VOLCANO) {
            String errorMessage = "A settlement cannot be built on a volcano.";
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        if (this.getHeight() < Settings.START_PLAYGROUND_HEX_HEIGHT_REQUIREMENT) {
            String errorMessage = "Hex does not meet height requirement to start a playground.";
            throw new SettlementHeightRequirementException(errorMessage);
        }

        if (this.settlement != null) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }
    }

    public void checkSanctuaryConditions() throws
            SettlementCannotBeBuiltOnVolcanoException,
            SettlementAlreadyExistsOnHexException
    {
        if (this.terrain == Terrain.VOLCANO) {
            String errorMessage = "A settlement cannot be built on a volcano.";
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        if (this.settlement != null) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }
    }
}
