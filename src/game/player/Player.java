package game.player;

import game.player.exceptions.NotEnoughPiecesException;
import game.settlements.exceptions.*;
import tile.*;
import game.settlements.*;

import java.util.ArrayList;

public class Player {
    private static final int STARTING_SCORE_COUNT = 0;
    private static final int FOUND_SETTLEMENT_POINTS = 1;
    private static final int BUILD_SANCTUARY_POINTS = 200;
    private static final int BUILD_PLAYGROUND_POINTS = 75;
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;

    private int score;
    private int totoroCount = STARTING_TOTORO_COUNT;
    private int tigerCount = STARTING_TIGER_COUNT;
    private int villagerCount = STARTING_VILLAGER_COUNT;
    private SettlementManager settlementManager = null;

    public Player(){
        this.score = STARTING_SCORE_COUNT;
    };

    public int getScore() {
        return this.score;
    }

    private void incrementScore(int changes) {
        this.score += changes;
    }

    public void playVillager(int count) throws NotEnoughPiecesException {
        if (getRemainingVillagerCount() < count) {
            String errorMessage = String.format("You do not have enough Villagers");
            // throw error
            return;
        }

        villagerCount -= count;
    }

    public void playATotoro() throws NotEnoughPiecesException {
        if (getRemainingTotoroCount() == 0) {
            String errorMessage = String.format("You do not have enough Totoro");
            // throw error
            return;
        }

        totoroCount--;
    }

    public void playATiger() throws NotEnoughPiecesException {
        if (getRemainingTigerCount() == 0) {
            String errorMessage = String.format("You do not have enough Tigers");
            // throw error
            return;
        }

        tigerCount--;
    }

    public int getRemainingTotoroCount(){
        return totoroCount;
    }

    public int getRemainingTigerCount(){
        return tigerCount;
    }

    public int getRemainingVillagerCount(){
        return villagerCount;
    }

    public void foundSettlement(Hex hexToBuildOn) {
        try {
            this.playVillager(1);
            this.settlementManager.foundSettlement(hexToBuildOn);
            hexToBuildOn.setIsOccupied(true);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }
        catch (HexIsOccupiedException e) {
            System.out.println("Hex is occupied: " + e);
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println("Settlement cannot be built on a volcano: " + e);
        }
        catch (SettlementHeightRequirementException e) {
            System.out.println("Settlement does not meet height requirements: " + e);
        }

        this.incrementScore(FOUND_SETTLEMENT_POINTS);
    }

    public void expandSettlement(VillagerSettlement existingSettlement, Terrain terrainType) {
        int numVillagers = 0;

        try {
            ArrayList<Hex> potentialSettlementHexes = this.settlementManager.getPotentialExpansion(existingSettlement, terrainType);

            for (int i=0; i<potentialSettlementHexes.size(); i++){
                int hexHeight = potentialSettlementHexes.get(i).getHeight();
                numVillagers += hexHeight;
            }

            this.playVillager(numVillagers);
            this.settlementManager.expandSettlement(existingSettlement, potentialSettlementHexes);

            for (int i=0; i<potentialSettlementHexes.size(); i++){
                Hex potentialSettlementHex = potentialSettlementHexes.get(i);
                potentialSettlementHex.setIsOccupied(true);
            }
        }

        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println("Settlement cannot be built on a volcano: " + e);
        }

        catch (SettlementCannotBeExpandedException e) {
            System.out.println("Settlement cannot be expanded: " + e);
        }

        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }

        this.incrementScore(numVillagers);
    }

    public void buildTotoroSanctuary(Hex hexToBuildOn) {
        try{
            this.playATotoro();
            this.settlementManager.buildTotoroSanctuary(hexToBuildOn);
            hexToBuildOn.setIsOccupied(true);
        }

        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }

        catch (HexIsOccupiedException e) {
            System.out.println("Hex is occupied: " + e);
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println("Sanctuary cannot be built on a volcano: " + e);
        }

        catch (BuildConditionsNotMetException e) {
            System.out.println("Sanctuary conditions are not met: " + e);
        }

        this.incrementScore(BUILD_SANCTUARY_POINTS);
    }

    public void buildTigerPlayground(Hex hexToBuildOn) {
        try{
            this.playATiger();
            this.settlementManager.buildTigerPlayground(hexToBuildOn);
            hexToBuildOn.setIsOccupied(true);
        }

        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }

        catch (HexIsOccupiedException e) {
            System.out.println("Hex is occupied: " + e);
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println("Playground cannot be built on a volcano: " + e);
        }

        catch (BuildConditionsNotMetException e) {
            System.out.println("Playground conditions are not met: " + e);
        }

        catch (SettlementHeightRequirementException e) {
            System.out.println("Hex does not meet height requirements: " + e);
        }

        this.incrementScore(BUILD_PLAYGROUND_POINTS);
    }

    public void mergeSettlements() {
        this.settlementManager.mergeSettlements();
    }
}