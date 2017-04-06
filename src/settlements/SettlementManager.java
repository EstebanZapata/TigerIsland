package settlements;

import game.world.CoordinateSystem;
import tile.*;
import java.util.ArrayList;

/**
 * Created by thomasbaldwin on 4/5/17.
 */

public class SettlementManager {
    ArrayList<VillagerSettlement> villagerSettlements;
    ArrayList<TigerPlayground> tigerPlaygrounds;
    ArrayList<TotoroSanctuary> totoroSanctuaries;

    public void foundSettlement(Hex hexToBuildOn) throws
            SettlementIsOccupiedException,
            SettlementCannotBeBuiltOnVolcanoException,
            SettlementHeightRequirementException
    {
        if (hexToBuildOn.isOccupied()) {
            String errorMessage = String.format("You do not have enough Villagers.");
            throw new SettlementIsOccupiedException(errorMessage);
        }

        if (hexToBuildOn.getTerrain() == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a settlement on a Volcano");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        if (hexToBuildOn.getHeight() != 1) {
            String errorMessage = String.format("Hex has to be of height 1 to build a settlement.");
            throw new SettlementHeightRequirementException(errorMessage);
        }

        VillagerSettlement newSettlement = new VillagerSettlement(hexToBuildOn);
        villagerSettlements.add(newSettlement);
    }

    public void expandSettlement(Hex hexToBuildOn, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException
    {
        if (terrainType == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a settlement on a Volcano");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        Location hexLocation = hexToBuildOn.getLocation();
        Location[] hexLocationsAdjacentToCenter = CoordinateSystem.getHexLocationsAdjacentToCenter(hexLocation);

        for (int i=0; i<hexLocationsAdjacentToCenter.length; i++) {
            Location adjacentHexLocation = hexLocationsAdjacentToCenter[i];

        }

    }
}
