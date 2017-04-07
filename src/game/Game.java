package game;


import game.settlements.VillagerSettlement;
import game.settlements.exceptions.HexIsOccupiedException;
import game.settlements.exceptions.SettlementCannotBeBuiltOnVolcanoException;
import game.settlements.exceptions.SettlementHeightRequirementException;
import game.world.*;
import game.world.exceptions.*;
import game.player.*;
import tile.*;
import tile.orientation.TileOrientation;

import java.util.ArrayList;

public class Game {
    private static final int START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT = 1;

    public World world;
    public Player player1;
    public Player player2;

    public Game() {
        this.world = new World();
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void play()  {
        Tile tile = drawTile();

        try {
            world.placeFirstTile(tile, TileOrientation.NORTHWEST_WEST);
            world.placeFirstTile(tile, TileOrientation.NORTHEAST_NORTHWEST);

            // player chooses expand option
            Hex hexesToExpandTo = getHexesToExpandToFromExistingSettlement(settlementToExpand, Terrain.GRASSLANDS);
            if (hexesToExpandTo == null) {
                // throw error, no hexes to expand to
            }

            expandSettlement(player1, settlementToExpand, hexesToExpandTo);
        }
        catch (TilePlacementException e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean canStartSettlementOnHex(Hex hexToStartSettlementOn, Player currentPlayer, Player otherPlayer) {
        if (otherPlayer.hasSettlementOnHex(hexToStartSettlementOn)) {
            return false;
        }

        if (hexToStartSettlementOn.getTerrain() == Terrain.VOLCANO) {
            return false;
        }

        if (hexToStartSettlementOn.getHeight() != START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT) {
            return false;
        }

        return true;
    }

    public void startSettlementOnHex(Hex hexToStartSettlementOn, Player currentPlayer) {
        currentPlayer.startSettlement(hexToStartSettlementOn);
    }

    public ArrayList<Hex> getHexesToExpandToFromExistingSettlement(VillagerSettlement existingSettlement, Terrain terrainType) {
        if (terrainType == Terrain.VOLCANO) {
            return null;
        }

        ArrayList<Hex> potentialSettlementHexes = null;
        ArrayList<Hex> settlementHexes = existingSettlement.getHexesFromSettlement();

        for (Hex hex : settlementHexes) {
            Location hexLocation = hex.getLocation();
            Location[] hexLocationsAdjacentToCenter = CoordinateSystem.getHexLocationsAdjacentToCenter(hexLocation);

            for (int j = 0; j < hexLocationsAdjacentToCenter.length; j++) {
                Location adjacentHexLocation = hexLocationsAdjacentToCenter[j];
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);

                if (adjacentHex.getTerrain() != terrainType) {
                    continue;
                }

                if (player1.hasSettlementOnHex(adjacentHex)) {
                    continue;
                }

                potentialSettlementHexes.add(adjacentHex);
            }
        }

        return potentialSettlementHexes;
    }

    public void expandSettlement(Player currentPlayer, VillagerSettlement existingSettlement, ArrayList<Hex> hexesToExpandTo) {
        currentPlayer.expandSettlement(existingSettlement, hexesToExpandTo);
    }
}
