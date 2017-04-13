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

    public Settlement(Hex foundingHex) throws
            SettlementAlreadyExistsOnHexException
    {
        this.settlementHexes = new ArrayList<Hex>();
        this.expansionQueue = new LinkedList<Hex>();
        this.addHexToSettlement(foundingHex);
    }

    public boolean containsHex(Hex hexToSearchFor) {
        return this.settlementHexes.contains(hexToSearchFor);
    }

    public int getSettlementSize(){
        return this.settlementHexes.size();
    }

    public ArrayList<Hex> getHexesFromSettlement() {
        return this.settlementHexes;
    }

    public void addHexToSettlement(Hex newHex) throws SettlementAlreadyExistsOnHexException {
        if (newHex.getSettlement() != null) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }

        this.settlementHexes.add(newHex);
        newHex.setSettlement(this);
    }

    public void removeHexFromSettlement(Hex hexToBeRemoved) throws SettlementCannotBeCompletelyWipedOutException {
        if (this.settlementHexes.size() > 1) {
            this.settlementHexes.remove(hexToBeRemoved);
            hexToBeRemoved.setSettlement(null);
        }

        else {
            String errorMessage = "A settlement cannot be completely wiped out.";
            throw new SettlementCannotBeCompletelyWipedOutException(errorMessage);
        }
    }

    public void setHasTotoroSanctuary() {
        this.hasTotoro = true;
    }

    public void setHasTigerPlayground() {
        this.hasTiger = true;
    }

    public Boolean hasTotoroSanctuary() {
        return this.hasTotoro;
    }

    public Boolean hasTigerPlayground() {
        return this.hasTiger;
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

        this.expansionQueue.addAll(settlementHexes);

        while (this.expansionQueue.size() != 0) {
            Hex hex = this.expansionQueue.poll();
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
            }
        }

        return allPotentialSettlementHexes;
    }

    public ArrayList<Hex> getPotentialSettlementHexes(Hex settlementHex, World world, Terrain terrainType) {
        ArrayList<Hex> potentialSettlementHexes = new ArrayList<>();
        Location hexLocation = settlementHex.getLocation();
        Location[] hexLocationsAdjacentToCenter = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(hexLocation);
        for (Location adjacentHexLocation : hexLocationsAdjacentToCenter) {
            try {
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                adjacentHex.checkExpansionConditions(terrainType);
                if (!this.expansionQueue.contains(adjacentHex))
                {
                    potentialSettlementHexes.add(adjacentHex);
                    this.expansionQueue.add(adjacentHex);
                }
            }
            catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
            catch (HexDoesNotMeetConditionsException e) {
                System.out.println(e.getMessage());
            }
            catch (SettlementAlreadyExistsOnHexException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            int x = hexLocation.getxCoordinate();
            int y = hexLocation.getyCoordinate();
            int height = hexLocation.getHeight();

            Hex nextLevelHex = world.getHexByCoordinate(x, y, height+1);
            nextLevelHex.checkExpansionConditions(terrainType);
            potentialSettlementHexes.add(nextLevelHex);
            this.expansionQueue.add(nextLevelHex);
        }
        catch (NoHexAtLocationException e) {
            System.out.println(e.getMessage());
        }
        catch (HexDoesNotMeetConditionsException e) {
            System.out.println(e.getMessage());
        }
        catch (SettlementAlreadyExistsOnHexException e) {
            System.out.println(e.getMessage());
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

