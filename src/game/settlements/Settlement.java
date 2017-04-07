package game.settlements;

import tile.*;
import java.util.ArrayList;

public class VillagerSettlement {
    private ArrayList<Hex> settlementHexes;

    private int numVillagers = 1;
    private int numTotoro = 0;
    private int numTigers = 0;

    public VillagerSettlement(Hex foundingHex) {
        settlementHexes.add(foundingHex);
    }

    public Boolean containsHex(Hex hexToSearchFor) {
        for (Hex settlementHex: settlementHexes) {
            if (settlementHex == hexToSearchFor) {
                return true;
            }
        }

        return false;
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

/*    public int getNumVillagers() {
        return numVillagers;
    }

    public void incrementNumVillagers(int amount) {
        numVillagers += amount;
    }*/

    public int getNumTotoroSanctuaries() {
        return numTotoro;
    }

    public void incrementNumTotoroSanctuaries() {
        numTotoro++;
    }

    public int getNumTigerPlaygrounds() {
        return numTigers;
    }

    public void incrementNumTigerPlaygrounds() {
        numTigers++;
    }
}
