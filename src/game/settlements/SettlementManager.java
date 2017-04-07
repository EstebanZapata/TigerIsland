package game.settlements;

import game.world.CoordinateSystem;
import game.settlements.exceptions.*;
import tile.*;
import java.util.ArrayList;

public class SettlementManager {
    public ArrayList<VillagerSettlement> villagerSettlements;

    public void startSettlement(Hex hexToStartSettlementOn) {
        VillagerSettlement newSettlement = new VillagerSettlement(hexToStartSettlementOn);
        villagerSettlements.add(newSettlement);
    }

    public Boolean hasSettlementOnHex(Hex hex) {
        for(VillagerSettlement settlement : villagerSettlements) {
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

    public ArrayList<Hex> getPotentialExpansion(VillagerSettlement existingSettlement, Terrain terrainType) {
        ArrayList<Hex> potentialSettlementHexes = null;

        if (terrainType == Terrain.VOLCANO) {
            return null;
        }

        for (int i=0; i<existingSettlement.getSettlementSize(); i++) {
            Hex settlementHex = existingSettlement.getHexFromSettlement(i);
            Location hexLocation = settlementHex.getLocation();
            Location[] hexLocationsAdjacentToCenter = CoordinateSystem.getHexLocationsAdjacentToCenter(hexLocation);

            for (int j=0; j<hexLocationsAdjacentToCenter.length; j++) {
                Location adjacentHexLocation = hexLocationsAdjacentToCenter[j];
                int adjacentXCoordinate = adjacentHexLocation.getxCoordinate();
                int adjacentYCoordinate = adjacentHexLocation.getyCoordinate();
                int adjacentZCoordinate = adjacentHexLocation.getzCoordinate();
                Hex adjacentHex = getHexByCoordinate(adjacentXCoordinate, adjacentYCoordinate, adjacentZCoordinate);

                if (adjacentHex.getTerrain() == terrainType) {
                    if (!adjacentHex.isOccupied()) {
                        potentialSettlementHexes.add(adjacentHex);
                    }
                }
            }
        }

        return potentialSettlementHexes;
    }

    public void expandSettlement(VillagerSettlement existingSettlement, ArrayList<Hex> potentialSettlementHexes) {
    //    int newVillagers = 0;

        for (int i=0; i<potentialSettlementHexes.size(); i++) {
            existingSettlement.addHexToSettlement(potentialSettlementHexes.get(i));

 /*         int hexHeight = potentialSettlementHexes.get(i).getHeight();
            newVillagers += hexHeight;*/
        }

       // existingSettlement.incrementNumVillagers(newVillagers);
    }

    public void buildTotoroSanctuary(Hex hexToBuildOn) throws
            HexIsOccupiedException,
            SettlementCannotBeBuiltOnVolcanoException,
            BuildConditionsNotMetException
    {
        boolean approvedHex = false;
        VillagerSettlement adjacentSettlement = null;

        if (hexToBuildOn.isOccupied()) {
            String errorMessage = String.format("Hex is already occupied.");
            throw new HexIsOccupiedException(errorMessage);
        }

        if (hexToBuildOn.getTerrain() == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a sanctuary on a Volcano");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        Location hexLocation = hexToBuildOn.getLocation();
        Location[] hexLocationsAdjacentToCenter = CoordinateSystem.getHexLocationsAdjacentToCenter(hexLocation);

        for (int i=0; i<hexLocationsAdjacentToCenter.length; i++) {
            Location adjacentHexLocation = hexLocationsAdjacentToCenter[i];
            int adjacentXCoordinate = adjacentHexLocation.getxCoordinate();
            int adjacentYCoordinate = adjacentHexLocation.getyCoordinate();
            int adjacentZCoordinate = adjacentHexLocation.getzCoordinate();
            Hex adjacentHex = getHexByCoordinate(adjacentXCoordinate, adjacentYCoordinate, adjacentZCoordinate);

            for (int j=0; j<villagerSettlements.size(); j++) {
                VillagerSettlement potentialSanctuary = villagerSettlements.get(j);

                if ((potentialSanctuary.getSettlementSize() >= 5) && (potentialSanctuary.getNumTotoroSanctuaries() < 1))
                {
                    for (int k=0; k<potentialSanctuary.getSettlementSize(); k++) {
                        if (potentialSanctuary.getHexFromSettlement(k) == adjacentHex) {
                            approvedHex = true;
                            adjacentSettlement = potentialSanctuary;
                        }
                    }
                }
            }
        }

        if (!approvedHex) {
            String errorMessage = String.format("This hex is not adjacent to a settlement of size >= 5 missing a totoro.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        adjacentSettlement.incrementNumTotoroSanctuaries();
    }

    public void buildTigerPlayground(Hex hexToBuildOn) throws
            HexIsOccupiedException,
            SettlementCannotBeBuiltOnVolcanoException,
            SettlementHeightRequirementException,
            BuildConditionsNotMetException
    {
        boolean approvedHex = false;
        VillagerSettlement adjacentSettlement = null;

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

            for (int j=0; j<villagerSettlements.size(); j++) {
                VillagerSettlement potentialPlayground = villagerSettlements.get(j);

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

        for (int i = 0; i < villagerSettlements.size(); i++) {
            VillagerSettlement settlement = villagerSettlements.get(i);

            for (int j = 0; j < settlement.getSettlementSize(); j++) {
                // ???
            }
        }
    }
}
