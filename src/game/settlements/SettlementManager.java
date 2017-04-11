package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.*;
import game.tile.*;
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

    public void buildTotoroSanctuary(Hex sanctuaryHex) throws NoPlayableHexException {
        Location sanctuaryHexLocation = sanctuaryHex.getLocation();
        Location[] adjacentHexLocationsToSanctuaryHex = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(sanctuaryHexLocation);
        for (Location adjacentHexLocation : adjacentHexLocationsToSanctuaryHex) {
            try {
                Hex adjacentHex = this.world.getHexByLocation(adjacentHexLocation);
                adjacentHex.checkSanctuaryConditions();
                Settlement settlement = getSettlementFromHex(adjacentHex);
                settlement.checkSanctuarySettlementConditions();
                settlement.setHasTotoroSanctuary();
                settlement.addHexToSettlement(sanctuaryHex);
                return;
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
            catch (NoSettlementOnHexException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementDoesNotSizeRequirementsException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementAlreadyHasTotoroSanctuaryException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementAlreadyExistsOnHexException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementCannotBeBuiltOnVolcanoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void buildTigerPlayground(Hex playgroundHex) throws
            SettlementAlreadyHasTigerPlaygroundException,
            NoSettlementOnAdjacentHexesException
    {
        Location playgroundHexLocation = playgroundHex.getLocation();
        Location[] adjacentHexLocationsToPlaygroundHex = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(playgroundHexLocation);
        for (Location adjacentHexLocation : adjacentHexLocationsToPlaygroundHex) {
            try {
                int xCoordinate = adjacentHexLocation.getxCoordinate();
                int yCoordinate = adjacentHexLocation.getyCoordinate();
                Hex adjacentHex = this.world.getHexRegardlessOfHeight(xCoordinate, yCoordinate);
                playgroundHex.checkPlaygroundConditions();
                Settlement settlement = getSettlementFromHex(adjacentHex);
                settlement.checkPlaygroundSettlementConditions();
                settlement.setHasTigerPlayground();
                settlement.addHexToSettlement(playgroundHex);
                return;
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementAlreadyHasTigerPlaygroundException e) {
                System.out.println(e.getMessage());
            }
            catch (NoSettlementOnHexException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementAlreadyExistsOnHexException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementCannotBeBuiltOnVolcanoException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementHeightRequirementException e) {
                System.out.println(e.getMessage());
            }
        }

        String errorMessage = String.format("There are no settlements on any adjacent hexes.");
        throw new NoSettlementOnAdjacentHexesException(errorMessage);
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

    /*
    private Settlement chooseSettlementToExpandTo(World world, Terrain terrainType) {
        int maxPossibleExpansionHexes = 0;
        for (Settlement settlement : this.settlements) {
            try {
                ArrayList<Hex> jungleExpansionHexes = settlement.getHexesToExpandTo(world, Terrain.JUNGLE);
                ArrayList<Hex> grasslandsExpansionHexes = settlement.getHexesToExpandTo(world, Terrain.GRASSLANDS);
                ArrayList<Hex> lakeExpansionHexes = settlement.getHexesToExpandTo(world, Terrain.LAKE);
                ArrayList<Hex> rockyExpansionHexes = settlement.getHexesToExpandTo(world, Terrain.ROCKY);

                int max1 = Math.max(jungleExpansionHexes.size(), grasslandsExpansionHexes.size());
                int max2 = Math.max(lakeExpansionHexes.size(), rockyExpansionHexes.size());
                int max = Math.max(max1, max2);
                maxPossibleExpansionHexes = Math.max(max, maxPossibleExpansionHexes);
            }
            catch (SettlementCannotBeBuiltOnVolcanoException e) {
                System.out.println(e.getMessage());
            }
            catch (NoHexesToExpandToException e) {
                System.out.println(e.getMessage());
            }
        }

        return this.settlements.get(0);
    }

    private Hex chooseNewSanctuaryHex(World world) throws NoPlayableHexException {
        for (Settlement settlement : this.settlements) {
            try {
                settlement.checkSanctuarySettlementConditions();
            }
            catch (SettlementDoesNotSizeRequirementsException e) {
                continue;
            }
            catch (SettlementAlreadyHasTotoroSanctuaryException e) {
                continue;
            }

            ArrayList<Hex> settlementHexes = settlement.getHexesFromSettlement();
            for (Hex settlementHex : settlementHexes) {
                Location existingHexLocation = settlementHex.getLocation();
                Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(existingHexLocation);
                for (Location adjacentHexLocation : adjacentHexLocations) {
                    try {
                        Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                        adjacentHex.checkSanctuaryConditions();
                        return adjacentHex;
                    }
                    catch (NoHexAtLocationException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (SettlementCannotBeBuiltOnVolcanoException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (SettlementAlreadyExistsOnHexException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        String errorMessage = String.format("There are no playable hexes for the player to build a sanctuary on.");
        throw new NoPlayableHexException(errorMessage);
    }

    private Hex chooseNewPlaygroundHex(World world) throws NoPlayableHexException {
        for (Settlement settlement : this.settlements) {
            try {
                settlement.checkPlaygroundSettlementConditions();
            }
            catch (SettlementAlreadyHasTigerPlaygroundException e) {
                System.out.println(e.getMessage());
            }

            ArrayList<Hex> settlementHexes = settlement.getHexesFromSettlement();
            for (Hex settlementHex : settlementHexes) {
                Location existingHexLocation = settlementHex.getLocation();
                Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(existingHexLocation);
                for (Location adjacentHexLocation : adjacentHexLocations) {
                    try{
                        Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                        adjacentHex.checkPlaygroundConditions();
                        return adjacentHex;
                    }
                    catch (NoHexAtLocationException e) {
                        System.out.println(e.getMessage());
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
            }
        }

        String errorMessage = String.format("There are no playable hexes for the player to build a playground on.");
        throw new NoPlayableHexException(errorMessage);
    }

    public void mergeSettlements() {
        for (Settlement settlement : settlements) {
            for (Hex settlementHex : settlement.getHexesFromSettlement()) {
                Location settlementHexLocation = settlementHex.getLocation();
                Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(settlementHexLocation);
                for (Location adjacentHexLocation : adjacentHexLocations) {
                    try {
                        Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                    }
                    catch (NoHexAtLocationException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
    */
}