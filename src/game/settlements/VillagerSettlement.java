package game.settlements;

import tile.*;
import java.util.ArrayList;

public class VillagerSettlement {
    private ArrayList<Hex> settlementHexes;
   // private int numVillagers;
   private int numTotoro;
   private int numTigers;

    public VillagerSettlement(Hex foundingHex) {
        settlementHexes.add(foundingHex);
 //       numVillagers = 1;
        numTotoro = 0;
        numTigers = 0;
    }

    public int getSettlementSize(){
        return settlementHexes.size();
    }

    public Hex getHexFromSettlement(int index) {
        return settlementHexes.get(index);
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
