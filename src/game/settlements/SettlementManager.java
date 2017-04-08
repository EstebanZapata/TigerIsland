package game.settlements;

import game.tile.*;

import java.util.ArrayList;

public class SettlementManager {
    private ArrayList<Settlement> settlements;

    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public void foundSettlement(Hex foundingHex) {
        Settlement newSettlement = new Settlement(foundingHex);
        settlements.add(newSettlement);
        foundingHex.setSettlement(newSettlement);
    }

    public Boolean hasSettlementOnHex(Hex hex) {
        for(Settlement settlement : settlements) {
            if (settlement.containsHex(hex)) {
                return true;
            }
        }
        return false;
    }

    public void expandSettlement(Settlement existingSettlement, ArrayList<Hex> potentialSettlementHexes) {
        for (Hex potentialSettlementHex : potentialSettlementHexes) {
            existingSettlement.addHexToSettlement(potentialSettlementHex);
        }
    }

    public void buildTotoroSanctuary(Settlement existingSettlement, Hex newSanctuaryHex) {
        existingSettlement.setHasTotoroSanctuary(true);
        existingSettlement.addHexToSettlement(newSanctuaryHex);
    }

    public void buildTigerPlayground(Settlement existingSettlement, Hex newPlaygroundHex) {
        existingSettlement.setHasTigerPlayground(true);
        existingSettlement.addHexToSettlement(newPlaygroundHex);
    }

    public void mergeSettlements() {
 /*       for (int i = 0; i < settlements.size(); i++) {
            Settlement settlement = settlements.get(i);

            for (int j = 0; j < settlement.getSettlementSize(); j++) {
                // ???
            }
        }*/
    }
}