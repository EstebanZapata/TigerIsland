package game.settlements;

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

    public boolean hasTotoroSanctuary() {
        return hasTotoro;
    }

    public void setHasTotoroSanctuary(boolean status) {
        hasTotoro = status;
    }

    public boolean hasTigerPlayground() {
        return hasTiger;
    }

    public void setHasTigerPlayground(boolean status) {
        hasTiger = status;
    }
}
