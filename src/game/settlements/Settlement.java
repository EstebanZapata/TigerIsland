package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.tile.*;

import java.util.*;

public class Settlement {
    private ArrayList<Hex> settlementHexes;
    private boolean hasTotoro = false;
    private boolean hasTiger = false;
    private LinkedList<Hex> expansionQueue;
    private Location totoroLocation = null;
    private Location tigerLocation = null;

    public Settlement(Hex foundingHex) throws SettlementAlreadyExistsOnHexException {
        settlementHexes = new ArrayList<Hex>();
        expansionQueue = new LinkedList<Hex>();
        this.addHexToSettlement(foundingHex);
    }

    public boolean containsHex(Hex hexToSearchFor) {
        return settlementHexes.contains(hexToSearchFor);
    }

    public int getSettlementSize(){
        return settlementHexes.size();
    }

    public ArrayList<Hex> getHexesFromSettlement() {
        return settlementHexes;
    }

    public void addHexToSettlement(Hex newHex) throws SettlementAlreadyExistsOnHexException {
        if (newHex.getSettlement() != null) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }

        newHex.setSettlement(this);
        settlementHexes.add(newHex);
    }

    public void addHexToSettlementForMerging(Hex newHex)  {
        newHex.setSettlement(this);
        settlementHexes.add(newHex);
    }

    public void removeHexFromSettlement(Hex hexToBeRemoved) throws SettlementCannotBeCompletelyWipedOutException {
        if (settlementHexes.size() > 1) {
            settlementHexes.remove(hexToBeRemoved);
            hexToBeRemoved.setSettlement(null);
        }

        else {
            String errorMessage = "A settlement cannot be completely wiped out.";
            throw new SettlementCannotBeCompletelyWipedOutException(errorMessage);
        }
    }

    public void removeHexFromSettlementForMerging(Hex hexToBeRemoved)  {
        settlementHexes.remove(hexToBeRemoved);
    }

    public void setHasTotoroSanctuary() {
        hasTotoro = true;
    }

    public void setHasTigerPlayground() {
        hasTiger = true;
    }

    public Boolean hasTotoroSanctuary() {
        return this.hasTotoro;
    }

    public Boolean hasTigerPlayground() {
        return this.hasTiger;
    }

    public Location getTotoroLocation() {
        return this.totoroLocation;
    }

    public void setTotoroLocation(Location totoroLocation) {
        this.totoroLocation = totoroLocation;
    }

    public Location getTigerLocation() {
        return this.tigerLocation;
    }

    public void setTigerLocation(Location tigerLocation) {
        this.tigerLocation = tigerLocation;
    }

    public void checkPlaygroundConditions() throws SettlementAlreadyHasTigerPlaygroundException {
        if (this.hasTiger == true) {
            String errorMessage = "A tiger already exists on the settlement.";
            throw new SettlementAlreadyHasTigerPlaygroundException(errorMessage);
        }
    }

    public void checkSanctuaryConditions() throws
            SettlementDoesNotSizeRequirementsException,
            SettlementAlreadyHasTotoroSanctuaryException
    {
        if (this.settlementHexes.size() < 5) {
            String errorMessage = "The settlement does not meet the size requirement for a totoro sanctuary.";
            throw new SettlementDoesNotSizeRequirementsException(errorMessage);
        }

        if (this.hasTotoro) {
            String errorMessage = "A totoro already exists on the settlement.";
            throw new SettlementAlreadyHasTotoroSanctuaryException(errorMessage);
        }
    }

    public ArrayList<Hex> getHexesToExpandTo(World world, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        ArrayList<Hex> potentialSettlementHexes;
        ArrayList<Hex> allPotentialSettlementHexes = new ArrayList<>();
        ArrayList<Hex> visited = new ArrayList<>();

        if (terrainType == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a hex on a volcano.");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        expansionQueue.addAll(settlementHexes);

        while (expansionQueue.size() != 0) {
            Hex hex = expansionQueue.poll();
            if (!visited.contains(hex))
            {
                try {
                    potentialSettlementHexes = getPotentialSettlementHexes(hex, world, terrainType);
                    visited.add(hex);

                    for (Hex potentialSettlementHex : potentialSettlementHexes) {
                        if (!allPotentialSettlementHexes.contains(potentialSettlementHex)) {
                            allPotentialSettlementHexes.add(potentialSettlementHex);
                        }
                    }
                }
                catch (NullPointerException e) {
                    String errorMessage = String.format("There are no playable hexes for the player to expand to.");
                    throw new NoHexesToExpandToException(errorMessage);
                }
                catch (SettlementHeightRequirementException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return allPotentialSettlementHexes;
    }

    public ArrayList<Hex> getPotentialSettlementHexes(Hex settlementHex, World world, Terrain terrainType) throws SettlementHeightRequirementException {
        ArrayList<Hex> potentialSettlementHexes = new ArrayList<>();

        Location hexLocation = settlementHex.getLocation();
        Location[] hexLocationsAdjacentToCenter = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(hexLocation);

        for (Location adjacentHexLocation : hexLocationsAdjacentToCenter) {
            try {
                int x = adjacentHexLocation.getxCoordinate();
                int y = adjacentHexLocation.getyCoordinate();
                Hex adjacentHex = world.getHexRegardlessOfHeight(x, y);

                adjacentHex.checkExpansionConditions(terrainType);

                if (!expansionQueue.contains(adjacentHex)) {
                    potentialSettlementHexes.add(adjacentHex);
                    expansionQueue.add(adjacentHex);
                }

            } catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            } catch (HexDoesNotMeetConditionsException e) {
                System.out.println(e.getMessage());
            } catch (SettlementAlreadyExistsOnHexException e) {
                System.out.println(e.getMessage());
            }
        }

        return potentialSettlementHexes;
    }

    public Location[] getAllHexLocationsAdjacentToSettlement() {
        Set<Location> setWithoutDuplicates = new HashSet<>();
        for (Hex hex : this.settlementHexes) {
            Location hexLocation = hex.getLocation();
            Location[] locationsToAdd = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(hexLocation);
            setWithoutDuplicates.addAll(Arrays.asList(locationsToAdd));
        }
        return setWithoutDuplicates.toArray(new Location[0]);
    }

}
