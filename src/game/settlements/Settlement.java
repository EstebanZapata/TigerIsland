package game.settlements;

import game.settlements.exceptions.SettlementCannotBeCompletelyWipedOutException;
import tile.*;
import java.util.ArrayList;

public class Settlement {
    private ArrayList<Hex> settlementHexes;
    private boolean hasTotoro = false;
    private boolean hasTiger = false;

    public Settlement(Hex foundingHex) {
        settlementHexes = new ArrayList<>();
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

    public void addHexToSettlement(Hex newHex) {
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

    public boolean hasTotoroSanctuary() {
        return hasTotoro;
    }

    public void setHasTotoroSanctuary() {
        hasTotoro = true;
    }

    public boolean hasTigerPlayground() {
        return hasTiger;
    }

    public void setHasTigerPlayground() {
        hasTiger = true;
    }
}
