package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.*;
import game.tile.*;
import gherkin.lexer.Ar;

import java.util.ArrayList;

public class SettlementManager {
    public World world;
    public ArrayList<Settlement> settlements;

    public SettlementManager(World existingWorld) {
        this.world = existingWorld;
        this.settlements = new ArrayList<Settlement>();
    }

    public Settlement foundSettlement(Hex hex) throws SettlementAlreadyExistsOnHexException {
        Settlement newSettlement = new Settlement(hex);
        this.settlements.add(newSettlement);
        newSettlement = tryToMerge(hex);
        return newSettlement;
    }

    // Ai
    public Hex chooseHexForSettlement() throws NoPlayableHexException {
        ArrayList<Hex> hexes = world.getAllHexesInWorld();

        // for now, choose first possible hex that meets conditions
        for (Hex hex : hexes) {
            try {
                hex.checkFoundingConditions();
                return hex;
            }
            catch (SettlementCannotBeBuiltOnVolcanoException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementHeightRequirementException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementAlreadyExistsOnHexException e) {
                System.out.println(e.getMessage());
            }
        }

        String errorMessage = String.format("There are no playable hexes for the player to build a settlement on.");
        throw new NoPlayableHexException(errorMessage);
    }

    public Settlement getSettlementFromHex(Hex hex) throws NoSettlementOnHexException {
        for(Settlement settlement : settlements) {
            if (settlement.containsHex(hex)) {
                return settlement;
            }
        }

        String errorMessage = String.format("There are no settlements on the hex you passed in.");
        throw new NoSettlementOnHexException(errorMessage);
    }

    public int getNumberOfVillagersRequiredToExpand(Settlement existingSettlement, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        ArrayList<Hex> hexesToExpandTo = existingSettlement.getHexesToExpandTo(world, terrainType);
        int numVillagers = 0;
        for (Hex hex : hexesToExpandTo) {
            numVillagers += hex.getHeight() + 1;
        }
        return numVillagers;
    }

    public void expandSettlement(Settlement existingSettlement, Terrain terrainType) throws
            NoHexesToExpandToException,
            SettlementCannotBeBuiltOnVolcanoException
    {
        ArrayList<Hex> hexesToExpandTo = existingSettlement.getHexesToExpandTo(this.world, terrainType);
        for (Hex hexToExpandTo : hexesToExpandTo) {
            try {
                existingSettlement.addHexToSettlement(hexToExpandTo);
            }
            catch (SettlementAlreadyExistsOnHexException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void buildTotoroSanctuary(Hex sanctuaryHex) throws BuildConditionsNotMetException {
        sanctuaryHex.checkSanctuaryConditions();

        Location sanctuaryHexLocation = sanctuaryHex.getLocation();
        Location[] adjacentHexLocationsToSanctuaryHex = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(sanctuaryHexLocation);
        for (Location adjacentHexLocation : adjacentHexLocationsToSanctuaryHex) {
            try {
                Hex adjacentHex = this.world.getHexByLocation(adjacentHexLocation);
                Settlement adjacentSettlement = getSettlementFromHex(adjacentHex);
                adjacentSettlement.checkSanctuaryConditions();
                adjacentSettlement.setHasTotoroSanctuary();
                adjacentSettlement.addHexToSettlement(sanctuaryHex);
                return;
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
            catch (NoSettlementOnHexException e) {
                System.out.println(e.getMessage());
            }
            catch (BuildConditionsNotMetException e) {
                System.out.println(e.getMessage());
            }
        }

        String errorMessage = String.format("There are no adjacent settlements that meet the sanctuary conditions.");
        throw new BuildConditionsNotMetException(errorMessage);
    }

    public void buildTigerPlayground(Hex playgroundHex) throws BuildConditionsNotMetException {
        playgroundHex.checkPlaygroundConditions();

        Location playgroundHexLocation = playgroundHex.getLocation();
        Location[] adjacentHexLocationsToPlaygroundHex = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(playgroundHexLocation);
        for (Location adjacentHexLocation : adjacentHexLocationsToPlaygroundHex) {
            try {
                int xCoordinate = adjacentHexLocation.getxCoordinate();
                int yCoordinate = adjacentHexLocation.getyCoordinate();
                Hex adjacentHex = this.world.getHexRegardlessOfHeight(xCoordinate, yCoordinate);
                Settlement settlement = getSettlementFromHex(adjacentHex);
                settlement.checkPlaygroundConditions();
                settlement.setHasTigerPlayground();
                settlement.addHexToSettlement(playgroundHex);
                return;
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
            catch (NoSettlementOnHexException e) {
                System.out.println(e.getMessage());
            }
            catch (BuildConditionsNotMetException e) {
                System.out.println(e.getMessage());
            }
        }

        String errorMessage = String.format("There are no adjacent settlements that meet the playground conditions.");
        throw new BuildConditionsNotMetException(errorMessage);
    }

    public Settlement tryToMerge(Hex foundingSettlementHex) {
        Settlement initialSettlement = foundingSettlementHex.getSettlement();
        ArrayList<Hex> adjacentHexesWithSettlement = new ArrayList<>();
        int adjacentHexesWithSettlementCount = 0;
        Location settlementHexLocation = foundingSettlementHex.getLocation();
        Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(settlementHexLocation);

        for (Location adjacentHexLocation : adjacentHexLocations) {
            try {
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);

                if (adjacentHex.getSettlement() != null) {
                    adjacentHexesWithSettlement.add(adjacentHex);
                }
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
        }

        adjacentHexesWithSettlementCount = adjacentHexesWithSettlement.size();
        if (adjacentHexesWithSettlementCount > 0) {
            Hex firstAdjacentHexWithSettlement = adjacentHexesWithSettlement.get(0);
            Settlement firstAdjacentSettlement = firstAdjacentHexWithSettlement.getSettlement();

            for (Hex adjacentHexWithSettlement : adjacentHexesWithSettlement) {
                Settlement nextAdjacentSettlement = adjacentHexWithSettlement.getSettlement();
                if (adjacentHexWithSettlement != firstAdjacentHexWithSettlement) {
                    nextAdjacentSettlement.removeHexFromSettlementForMerging(adjacentHexWithSettlement);
                }
                adjacentHexWithSettlement.setSettlement(firstAdjacentSettlement);
            }

            foundingSettlementHex.setSettlement(firstAdjacentSettlement);
            initialSettlement.removeHexFromSettlementForMerging(foundingSettlementHex);
            return firstAdjacentSettlement;
        }

        return initialSettlement;
    }

    public int sizeOfLargestContainedSettlement() {
        int sizeOfLargestSettlement = 0;
        for (Settlement settlement : settlements) {
            sizeOfLargestSettlement = Math.max(settlement.getSettlementSize(), sizeOfLargestSettlement);
        }
        return sizeOfLargestSettlement;
    }

    public Settlement getLargestContainedSettlement() {
        int sizeOfLargestSettlement = 0;
        Settlement largestSettlement = null;
        for(Settlement settlement : settlements) {
            if(settlement.getSettlementSize() > sizeOfLargestSettlement) {
                sizeOfLargestSettlement = settlement.getSettlementSize();
                largestSettlement = settlement;
            }
        }
        return largestSettlement;
    }

    public Settlement getLargestSettlementNotContainingATotoro() {
        int sizeOfLargestSettlement = 0;
        Settlement largestSettlement = null;
        for(Settlement settlement : settlements) {
            if(settlement.getSettlementSize() > sizeOfLargestSettlement && !settlement.hasTotoroSanctuary()) {
                sizeOfLargestSettlement = settlement.getSettlementSize();
                largestSettlement = settlement;
            }
        }
        return largestSettlement;
    }

}