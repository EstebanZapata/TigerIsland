package game;

import pieces.*;
import settlements.SettlementCannotBeBuiltOnVolcanoException;
import settlements.SettlementHeightRequirementException;
import settlements.SettlementIsOccupiedException;
import tile.*;
import settlements.*;

import java.util.Vector;

public class Player {
    private static final int STARTING_SCORE_COUNT = 0;
    private static final int FOUND_SETTLEMENT_POINTS = 1;
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;

    private int score;
    private Vector<Totoro> totoros = new Vector<Totoro>(STARTING_TOTORO_COUNT);
    private Vector<Tiger> tigers = new Vector<Tiger>(STARTING_TIGER_COUNT);
    private Vector<Villager> villagers = new Vector<Villager>(STARTING_VILLAGER_COUNT);
    private SettlementManager settlementManager;

    public Player(){
        this.score = STARTING_SCORE_COUNT;

        for (int i = 0; i<STARTING_TOTORO_COUNT; i++){
            totoros.add(i,new Totoro(new Location(0,0,0),false));
        }
        for (int i = 0; i<STARTING_TIGER_COUNT; i++){
            tigers.add(i,new Tiger(new Location(0,0,0),false));
        }
        for (int i = 0; i<STARTING_VILLAGER_COUNT; i++){
            villagers.add(i,new Villager(new Location(0,0,0),false));
        }
    };

    public int getScore() {
        return this.score;
    }

    public void playAVillager() throws NotEnoughPiecesException {
        if (getRemainingVillagerCount() == 0) {
            String errorMessage = String.format("You do not have enough Villagers");
            // throw error
            return;
        }

        villagers.remove(0);
    }

    public void playATotoro() throws NotEnoughPiecesException {
        if (getRemainingTotoroCount() == 0) {
            String errorMessage = String.format("You do not have enough Totoros");
            // throw error
            return;
        }

        totoros.remove(0);
    }

    public void playATiger() throws NotEnoughPiecesException {
        if (getRemainingTigerCount() == 0) {
            String errorMessage = String.format("You do not have enough Tigers");
            // throw error
            return;
        }

        tigers.remove(0);
    }

    public int getRemainingTotoroCount(){
        return totoros.size();
    }

    public int getRemainingTigerCount(){
        return tigers.size();
    }

    public int getRemainingVillagerCount(){
        return villagers.size();
    }

    public void foundSettlement(Hex hexToBuildOn) {
        try {
            this.playAVillager();
            this.settlementManager.foundSettlement(hexToBuildOn);
            this.incrementScore(FOUND_SETTLEMENT_POINTS);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }
        catch (SettlementIsOccupiedException e) {
            System.out.println("Settlement is occupied: " + e);
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println("Settlement cannot be built on a volcano: " + e);
        }
        catch (SettlementHeightRequirementException e) {
            System.out.println("Settlement does not meet height requirements: " + e);
        }
    }

    public void buildTotoroSanctuary(Hex hexToBuildOn) {

    }

    public void buildTigerPlayground(Hex hexToBuildOn) {

    }

    public void expandSettlement(Hex hexToBuildOn) {

    }

    private void incrementScore(int changes) {
        this.score += changes;
    }
}
