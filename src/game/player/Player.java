package game.player;

import game.player.exceptions.NotEnoughPiecesException;
import game.settlements.exceptions.*;
import tile.*;
import game.settlements.*;

import java.util.ArrayList;

public class Player {
    private static final int FOUND_SETTLEMENT_POINTS = 1;
    private static final int BUILD_SANCTUARY_POINTS = 200;
    private static final int BUILD_PLAYGROUND_POINTS = 75;

    private static final int STARTING_SCORE_COUNT = 0;
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;

    private int score = STARTING_SCORE_COUNT;;
    private int totoroCount = STARTING_TOTORO_COUNT;;
    private int tigerCount = STARTING_TIGER_COUNT;;
    private int villagerCount = STARTING_VILLAGER_COUNT;;

    public SettlementManager settlementManager = null;

    public int getScore() {
        return this.score;
    }

    private void incrementScore(int changes) {
        this.score += changes;
    }

    public SettlementManager getSettlementManager() {
        if (this.settlementManager == null) {
            settlementManager = new SettlementManager();
        }
        return settlementManager;
    }

    public Boolean hasSettlementOnHex(Hex hexToSearchFor) {
        return settlementManager.hasSettlementOnHex(hexToSearchFor);
    }

    public int getNumberOfVillagers() {
        return villagerCount;
    }

    public void playVillagers(int count) throws NotEnoughPiecesException {
        if (this.villagerCount < count) {
            String errorMessage = String.format("You do not have enough Villagers");
            // throw error
            return;
        }

        villagerCount -= count;
    }

    public void playATotoro() throws NotEnoughPiecesException {
        if (this.totoroCount == 0) {
            String errorMessage = String.format("You do not have enough Totoro");
            // throw error
            return;
        }

        totoroCount--;
    }

    public void playATiger() throws NotEnoughPiecesException {
        if (this.tigerCount == 0) {
            String errorMessage = String.format("You do not have enough Tigers");
            // throw error
            return;
        }

        tigerCount--;
    }

    public void startSettlement(Hex hexToStartSettlementOn) {
        try {
            this.playVillagers(1);
            getSettlementManager().startSettlement(hexToStartSettlementOn);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }

        this.incrementScore(FOUND_SETTLEMENT_POINTS);
    }



    public void expandSettlement(Settlement existingSettlement, ArrayList<Hex> hexesToExpandTo) {
        try {
            int numberOfVillagersRequired = getSettlementManager().getNumberOfVillagersRequiredToExpandToHexes(hexesToExpandTo);
            this.playVillagers(numberOfVillagersRequired);
            getSettlementManager().expandSettlement(existingSettlement, hexesToExpandTo);
            this.incrementScore(numberOfVillagersRequired);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println("Not enough pieces: " + e);
        }
    }

    public void buildTotoroSanctuary(Hex hexToBuildOn) {
        try {
            this.playATotoro();
            getSettlementManager().buildTotoroSanctuary(hexToBuildOn);
            hexToBuildOn.setIsOccupied(true);
            this.incrementScore(BUILD_SANCTUARY_POINTS);
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
    }

    public void buildTigerPlayground(Hex hexToBuildOn) {
        try {
            this.playATiger();
            getSettlementManager().buildTigerPlayground(hexToBuildOn);
            hexToBuildOn.setIsOccupied(true);
            this.incrementScore(BUILD_PLAYGROUND_POINTS);
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
    }

    public void mergeSettlements() {
        getSettlementManager().mergeSettlements();
    }
}