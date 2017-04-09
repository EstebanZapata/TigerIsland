package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.tile.*;
import java.util.ArrayList;

public class Settlement {
    private ArrayList<Hex> settlementHexes;
    private boolean hasTotoro = false;
    private boolean hasTiger = false;

    public Settlement(Hex foundingHex) {
        settlementHexes = new ArrayList<Hex>();
        settlementHexes.add(foundingHex);
        foundingHex.setSettlement(this);
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
        if (this.settlementHexes.contains(newHex)) {
            String errorMessage = "A settlement already exists on the hex.";
            throw new SettlementAlreadyExistsOnHexException(errorMessage);
        }

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

    public void checkPlaygroundSettlementConditions() throws SettlementAlreadyHasTigerPlaygroundException {
        if (this.hasTiger == true) {
            String errorMessage = "A tiger already exists on the settlement.";
            throw new SettlementAlreadyHasTigerPlaygroundException(errorMessage);
        }
    }

    public void checkSanctuarySettlementConditions() throws
            SettlementDoesNotSizeRequirementsException,
            SettlementAlreadyHasTotoroSanctuaryException
    {
        if (this.settlementHexes.size() < 5) {
            String errorMessage = "The settlement does not meet the size requirement for a totoro sanctuary.";
            throw new SettlementDoesNotSizeRequirementsException(errorMessage);
        }

        if (this.hasTotoro == true) {
            String errorMessage = "A tiger already exists on the settlement.";
            throw new SettlementAlreadyHasTotoroSanctuaryException(errorMessage);
        }
    }

    public ArrayList<Hex> getHexesToExpandTo(World world, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        ArrayList<Hex> potentialSettlementHexes = new ArrayList<>();

        if (terrainType == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a hex on a volcano.");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        for (Hex settlementHex : this.settlementHexes) {
            try {
                potentialSettlementHexes = getPotentialSettlementHexes(settlementHex, world, terrainType, potentialSettlementHexes);
            }
            catch (NullPointerException e) {
                String errorMessage = String.format("There are no playable hexes for the player to expand to.");
                throw new NoHexesToExpandToException(errorMessage);
            }
        }

        return potentialSettlementHexes;
    }

    public ArrayList<Hex> getPotentialSettlementHexes(Hex settlementHex, World world, Terrain terrainType, ArrayList<Hex> potentialSettlementHexes) {
        Location hexLocation = settlementHex.getLocation();
        Location[] hexLocationsAdjacentToCenter = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(hexLocation);
        for (Location adjacentHexLocation : hexLocationsAdjacentToCenter) {
            try {
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                adjacentHex.checkExpansionConditions(terrainType);
                if (!settlementHexes.contains(adjacentHex) && (!potentialSettlementHexes.contains(adjacentHex)))
                {
                    potentialSettlementHexes.add(adjacentHex);
                    return getPotentialSettlementHexes(adjacentHex, world, terrainType, potentialSettlementHexes);
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
        return potentialSettlementHexes;
    }
}
