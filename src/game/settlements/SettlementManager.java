package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.*;
import game.tile.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class SettlementManager {
    public World world;
    public ArrayList<Settlement> settlements;
    private LinkedList<Hex> expansionQueue;

    public SettlementManager(World existingWorld) {
        this.world = existingWorld;
        this.settlements = new ArrayList<Settlement>();
        this.expansionQueue = new LinkedList<Hex>();
    }

    public Settlement foundSettlement(Hex hex) throws SettlementAlreadyExistsOnHexException {
        Settlement newSettlement = new Settlement(hex);
        this.settlements.add(newSettlement);
        newSettlement = tryToMergeAfterFounding(hex);
        settlementListPostMergeCleaner();
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
        if (hex.getSettlement() == null) {
            String errorMessage = String.format("There are no settlements on the hex you passed in.");
            throw new NoSettlementOnHexException(errorMessage);
        }

        return hex.getSettlement();
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

        tryToMergeAfterExpanding(existingSettlement);
        settlementListPostMergeCleaner();
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
                adjacentSettlement.setTotoroLocation(sanctuaryHexLocation);
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
                Settlement adjacentSettlement = getSettlementFromHex(adjacentHex);
                adjacentSettlement.checkPlaygroundConditions();
                adjacentSettlement.setHasTigerPlayground();
                adjacentSettlement.addHexToSettlement(playgroundHex);
                adjacentSettlement.setTigerLocation(playgroundHexLocation);
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

    public Settlement tryToMergeAfterFounding(Hex foundingSettlementHex) {
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

            for (int i=0; i<adjacentHexesWithSettlementCount; i++) {
                Hex adjacentHexWithSettlement = adjacentHexesWithSettlement.get(i);
                Settlement nextAdjacentSettlement = adjacentHexWithSettlement.getSettlement();
                if (adjacentHexWithSettlement != firstAdjacentHexWithSettlement) {
                    nextAdjacentSettlement.removeHexFromSettlementForMerging(adjacentHexWithSettlement);
                }

                firstAdjacentSettlement.addHexToSettlementForMerging(adjacentHexWithSettlement);
            }

            firstAdjacentSettlement.addHexToSettlementForMerging(foundingSettlementHex);
            initialSettlement.removeHexFromSettlementForMerging(foundingSettlementHex);
            return firstAdjacentSettlement;
        }

        return initialSettlement;
    }

    public void tryToMergeAfterExpanding(Settlement expandedSettlement) {
        ArrayList<Settlement> adjacentSettlements = new ArrayList<>();
        ArrayList<Integer> adjacentSettlementSizes = new ArrayList<>();
        ArrayList<Settlement> allAdjacentSettlements = new ArrayList<>();
        ArrayList<Hex> visited = new ArrayList<>();

        ArrayList<Hex> settlementHexes = expandedSettlement.getHexesFromSettlement();

        expansionQueue.addAll(settlementHexes);

        while (expansionQueue.size() != 0) {
            Hex hex = expansionQueue.poll();

            if (!visited.contains(hex)) {
                adjacentSettlements = getAdjacentSettlements(hex);
                visited.add(hex);

                for (Settlement adjacentSettlement : adjacentSettlements) {
                    if (!allAdjacentSettlements.contains(adjacentSettlement)) {
                        allAdjacentSettlements.add(adjacentSettlement);
                        adjacentSettlementSizes.add(adjacentSettlement.getSettlementSize());
                    }
                }
            }
        }

        if (allAdjacentSettlements.size() > 0) {
            int largestSettlementSize = Collections.max(adjacentSettlementSizes);
            int index = adjacentSettlementSizes.indexOf(largestSettlementSize);
            Settlement largestAdjacentSettlement = allAdjacentSettlements.get(index);

            if (expandedSettlement.getSettlementSize() < largestSettlementSize ) {
                ArrayList<Hex> expandedSettlementHexes = expandedSettlement.getHexesFromSettlement();
                for (Hex expandedSettlementHex : expandedSettlementHexes) {
                    expandedSettlement.removeHexFromSettlementForMerging(expandedSettlementHex);
                    largestAdjacentSettlement.addHexToSettlementForMerging(expandedSettlementHex);
                }
                for (Settlement adjacentSettlement : allAdjacentSettlements) {
                    if (adjacentSettlement != largestAdjacentSettlement) {
                        ArrayList<Hex> adjacentSettlementHexes = adjacentSettlement.getHexesFromSettlement();
                        for (int i=0; i<adjacentSettlementHexes.size(); i++) {
                            Hex adjacentSettlementHex = adjacentSettlementHexes.get(i);
                            adjacentSettlement.removeHexFromSettlementForMerging(adjacentSettlementHex);
                            largestAdjacentSettlement.addHexToSettlementForMerging(adjacentSettlementHex);
                        }
                    }
                }
            }

            else {  // expanded settlement is largest settlement
                for (Settlement adjacentSettlement : allAdjacentSettlements) {
                    ArrayList<Hex> adjacentSettlementHexes = adjacentSettlement.getHexesFromSettlement();
                    for (int i=0; i<adjacentSettlementHexes.size(); i++) {
                        Hex adjacentSettlementHex = adjacentSettlementHexes.get(i);
                        adjacentSettlement.removeHexFromSettlementForMerging(adjacentSettlementHex);
                        expandedSettlement.addHexToSettlementForMerging(adjacentSettlementHex);
                    }
                }
            }
        }
    }

    public ArrayList<Settlement> getAdjacentSettlements(Hex hexFromExpandingSettlement) {
        ArrayList<Settlement> adjacentSettlements = new ArrayList<>();
        Location settlementHexLocation = hexFromExpandingSettlement.getLocation();
        Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(settlementHexLocation);

        for (Location adjacentHexLocation : adjacentHexLocations) {
            try {
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);

                if (adjacentHex.getSettlement() != null) {
                    if (!expansionQueue.contains(adjacentHex)) {
                        adjacentSettlements.add(adjacentHex.getSettlement());
                        expansionQueue.add(adjacentHex);
                    }
                }
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
        }

        return adjacentSettlements;
    }

    public void settlementListPostMergeCleaner() {
        if (settlements.size() > 1) {
            for (int i=0; i<settlements.size(); i++) {
                Settlement settlement = settlements.get(i);
                if (settlement.getSettlementSize() == 0) {
                    settlements.remove(settlement);
                }
            }
        }
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

  /*  public void splitSettlementsIfNuked() {

    }*/

}