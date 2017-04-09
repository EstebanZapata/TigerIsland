package game.settlements;

import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.NoHexAtLocationException;
import tile.*;
import java.util.ArrayList;

public class Settlement {
    private ArrayList<Hex> settlementHexes;
    private boolean hasTotoro = false;
    private boolean hasTiger = false;

    public Settlement(Hex foundingHex) {
        settlementHexes = new ArrayList<Hex>();
        settlementHexes.add(foundingHex);
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

    public boolean checkPlaygroundConditions() {
        return (this.hasTiger == false);
    }

    public boolean checkSanctuaryConditions() {
        if (this.settlementHexes.size() < 5) {
            return false;
        }

        if (this.hasTotoro == true) {
            return false;
        }

        return true;
    }

    public ArrayList<Hex> getHexesToExpandTo(World world, Terrain terrainType) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        if (terrainType == Terrain.VOLCANO) {
            String errorMessage = String.format("You cannot build a hex on a volcano.");
            throw new SettlementCannotBeBuiltOnVolcanoException(errorMessage);
        }

        ArrayList<Hex> potentialSettlementHexes = new ArrayList<Hex>();
        for (Hex hex : this.settlementHexes) {
            Location hexLocation = hex.getLocation();
            Location[] hexLocationsAdjacentToCenter = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(hexLocation);
            for (Location adjacentHexLocation : hexLocationsAdjacentToCenter) {
                try {
                    Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                    if (!settlementHexes.contains(adjacentHex) && adjacentHex.checkExpansionConditions(terrainType))
                    {
                        potentialSettlementHexes.add(adjacentHex);
                    }
                }
                catch (NoHexAtLocationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (potentialSettlementHexes.isEmpty()) {
            String errorMessage = String.format("There are no playable hexes for the player to expand to.");
            throw new NoHexesToExpandToException(errorMessage);
        }

        return potentialSettlementHexes;
    }

    public Boolean hasTotoroSanctuary() {
        return this.hasTotoro;
    }

    public Boolean hasTigerPlayground() {
        return this.hasTiger;
    }
}
