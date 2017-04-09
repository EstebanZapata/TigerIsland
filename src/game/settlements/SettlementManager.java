package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.*;
import tile.*;
import java.util.ArrayList;

import static game.world.CoordinateSystemHelper.getHexLocationsAdjacentToCenter;

public class SettlementManager {
    public ArrayList<Settlement> settlements;

    public SettlementManager() {
        this.settlements = new ArrayList<Settlement>();
    }

    public Settlement foundSettlement(Hex hex) throws SettlementAlreadyExistsOnHexException {
        Settlement newSettlement = new Settlement(hex);
        this.settlements.add(newSettlement);
        return newSettlement;
    }

    // AI
    public Hex chooseHexForSettlement(World world) throws NoPlayableHexException {
        ArrayList<Hex> hexes = world.getAllHexesInWorld();

        // for now, choose first possible hex that meets conditions
        for (Hex hex : hexes) {
            if (hex.checkFoundingConditions()) {
                return hex;
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

    public int getNumberOfVillagersRequiredToExpand(World world, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        Settlement existingSettlement = chooseSettlementToExpandTo(world, terrainType);
        try {
            ArrayList<Hex> hexesToExpandTo = existingSettlement.getHexesToExpandTo(world, terrainType);
            int numVillagers = 0;
            for (Hex hex : hexesToExpandTo) {
                numVillagers += hex.getHeight() + 1;
            }
            return numVillagers;
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            throw new SettlementCannotBeBuiltOnVolcanoException(e.getMessage());
        }
        catch (NoHexesToExpandToException e) {
            throw new NoHexesToExpandToException(e.getMessage());
        }
    }

    public void expandSettlement(World world, Settlement existingSettlement, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        try {
            ArrayList<Hex> hexesToExpandTo = existingSettlement.getHexesToExpandTo(world, terrainType);
            for (Hex hexToExpandTo : hexesToExpandTo) {
                try {
                    existingSettlement.addHexToSettlement(hexToExpandTo);
                }
                catch (SettlementAlreadyExistsOnHexException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println(e.getMessage());
        }
        catch (NoHexesToExpandToException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildTotoroSanctuary(World world) throws NoPlayableHexException {
        try {
            Hex sanctuaryHex = chooseNewSanctuaryHex(world);
            Location sanctuaryHexLocation = sanctuaryHex.getLocation();
            Location[] adjacentHexLocationsToSanctuaryHex = getHexLocationsAdjacentToCenter(sanctuaryHexLocation);
            for (Location adjacentHexLocation : adjacentHexLocationsToSanctuaryHex) {
                try {
                    Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                    Settlement settlement = getSettlementFromHex(adjacentHex);
                    if (settlement.checkSanctuarySettlementConditions()) {
                        settlement.setHasTotoroSanctuary();
                        settlement.addHexToSettlement(sanctuaryHex);
                        return;
                    }
                }
                catch (NoHexAtLocationException e) {
                    System.out.println(e.getMessage());
                }
                catch (NoSettlementOnHexException e) {
                    System.out.println(e.getMessage());
                }
                catch (SettlementAlreadyExistsOnHexException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (NoPlayableHexException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildTigerPlayground(World world) throws NoPlayableHexException {
        try {
            Hex playgroundHex = chooseNewPlaygroundHex(world);

            Location playgroundHexLocation = playgroundHex.getLocation();
            Location[] adjacentHexLocationsToPlaygroundHex = getHexLocationsAdjacentToCenter(playgroundHexLocation);
            for (Location hexLocation : adjacentHexLocationsToPlaygroundHex) {
                try {
                    Hex adjacentHex = world.getHexByLocation(hexLocation);
                    Settlement settlement = getSettlementFromHex(adjacentHex);
                    if (settlement.checkPlaygroundSettlementConditions()) {
                        settlement.setHasTigerPlayground();
                        settlement.addHexToSettlement(playgroundHex);
                        return;
                    }
                }
                catch (NoHexAtLocationException e) {
                    System.out.println(e.getMessage());
                }
                catch (NoSettlementOnHexException e) {
                    System.out.println(e.getMessage());
                }
                catch (SettlementAlreadyExistsOnHexException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (NoPlayableHexException e) {
            System.out.println(e.getMessage());
        }
    }

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
            if (!settlement.checkSanctuarySettlementConditions()) {
                continue;
            }

            ArrayList<Hex> settlementHexes = settlement.getHexesFromSettlement();
            for (Hex settlementHex : settlementHexes) {
                Location existingHexLocation = settlementHex.getLocation();
                Location[] adjacentHexLocations = getHexLocationsAdjacentToCenter(existingHexLocation);
                for (Location adjacentHexLocation : adjacentHexLocations) {
                    try {
                        Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                        if (adjacentHex.checkSanctuaryConditions()) {
                            return adjacentHex;
                        }
                    }
                    catch (NoHexAtLocationException e) {
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
            if (!settlement.checkPlaygroundSettlementConditions()) {
                continue;
            }

            ArrayList<Hex> settlementHexes = settlement.getHexesFromSettlement();
            for (Hex settlementHex : settlementHexes) {
                Location existingHexLocation = settlementHex.getLocation();
                Location[] adjacentHexLocations = getHexLocationsAdjacentToCenter(existingHexLocation);
                for (Location adjacentHexLocation : adjacentHexLocations) {
                    try{
                        Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                        if (adjacentHex.checkPlaygroundConditions()) {
                            return adjacentHex;
                        }
                    }
                    catch (NoHexAtLocationException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        String errorMessage = String.format("There are no playable hexes for the player to build a playground on.");
        throw new NoPlayableHexException(errorMessage);
    }

    public void mergeSettlements(World world) {
        /*for (Settlement settlement : settlements) {
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
        }*/
    }
}