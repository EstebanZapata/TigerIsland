package game.settlements;

import game.world.CoordinateSystemHelper;
import game.settlements.exceptions.*;
import tile.*;

import java.util.ArrayList;

public class SettlementManager {
    private ArrayList<Settlement> settlements;

    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public void startSettlement(Hex hexToStartSettlementOn) {
        Settlement newSettlement = new Settlement(hexToStartSettlementOn);
        settlements.add(newSettlement);
    }

    public Boolean hasSettlementOnHex(Hex hex) {
        for(Settlement settlement : settlements) {
            if (settlement.containsHex(hex)) {
                return true;
            }
        }

        return false;
    }

    public int getNumberOfVillagersRequiredToExpandToHexes(ArrayList<Hex> hexesToExpandTo) {
        int numVillagers = 0;

        for (Hex hex : hexesToExpandTo) {
            int hexHeight = hex.getHeight();
            numVillagers += hexHeight;
        }

        return numVillagers;
    }

    public void expandSettlement(Settlement existingSettlement, ArrayList<Hex> potentialSettlementHexes) {
    //    int newVillagers = 0;

        for (int i=0; i<potentialSettlementHexes.size(); i++) {
            existingSettlement.addHexToSettlement(potentialSettlementHexes.get(i));

 /*         int hexHeight = potentialSettlementHexes.get(i).getHeight();
            newVillagers += hexHeight;*/
        }

       // existingSettlement.incrementNumVillagers(newVillagers);
    }

    public void buildTigerPlayground(Hex hexToBuildOn) throws
            HexIsOccupiedException,
            SettlementCannotBeBuiltOnVolcanoException,
            SettlementHeightRequirementException,
            BuildConditionsNotMetException
    {
        boolean approvedHex = false;
        Settlement adjacentSettlement = null;

        if (hexToBuildOn.isOccupied()) {
            String errorMessage = String.format("Hex is already occupied.");
            throw new HexIsOccupiedException(errorMessage);
        }

        if (hexToBuildOn.getTerrain() == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a playground on a Volcano");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        int hexHeight = hexToBuildOn.getHeight();

        if (hexHeight < 3) {
            String errorMessage = String.format("Hex must be level 3 or greater to build a tiger playground.");
            throw new SettlementHeightRequirementException(errorMessage);
        }

        Location hexLocation = hexToBuildOn.getLocation();
        Location[] hexLocationsAdjacentToCenter = CoordinateSystem.getHexLocationsAdjacentToCenter(hexLocation);

        for (int i=0; i<hexLocationsAdjacentToCenter.length; i++) {
            Location adjacentHexLocation = hexLocationsAdjacentToCenter[i];
            int adjacentXCoordinate = adjacentHexLocation.getxCoordinate();
            int adjacentYCoordinate = adjacentHexLocation.getyCoordinate();
            int adjacentZCoordinate = adjacentHexLocation.getzCoordinate();
            Hex adjacentHex = getHexByCoordinate(adjacentXCoordinate, adjacentYCoordinate, adjacentZCoordinate);

            for (int j = 0; j< settlements.size(); j++) {
                Settlement potentialPlayground = settlements.get(j);

                if (potentialPlayground.getNumTigerPlaygrounds() < 1)
                {
                    for (int k=0; k<potentialPlayground.getSettlementSize(); k++) {
                        if (potentialPlayground.getHexFromSettlement(k) == adjacentHex) {
                            approvedHex = true;
                            adjacentSettlement = potentialPlayground;
                        }
                    }
                }
            }
        }

        if (!approvedHex) {
            String errorMessage = String.format("This hex is not adjacent to a settlement missing a tiger.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        adjacentSettlement.incrementNumTigerPlaygrounds();
    }

    public void mergeSettlements() {

        for (int i = 0; i < settlements.size(); i++) {
            Settlement settlement = settlements.get(i);

            for (int j = 0; j < settlement.getSettlementSize(); j++) {
                // ???
            }
        }
    }
}
