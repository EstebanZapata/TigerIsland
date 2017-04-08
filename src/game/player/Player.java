package game.player;

import game.player.exceptions.*;
import game.settlements.*;
import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.*;
import tile.*;

import java.util.ArrayList;

public class Player {
    public static final int FOUND_SETTLEMENT_POINTS = 1;
    public static final int BUILD_TOTORO_SANCTUARY_POINTS = 200;
    public static final int BUILD_TIGER_PLAYGROUND_POINTS = 75;

    private static final int STARTING_SCORE_COUNT = 0;
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;

    private int score = STARTING_SCORE_COUNT;
    private int totoroCount = STARTING_TOTORO_COUNT;
    private int tigerCount = STARTING_TIGER_COUNT;
    private int villagerCount = STARTING_VILLAGER_COUNT;

    public SettlementManager settlementManager;

    public Player() {
        this.settlementManager = new SettlementManager();
    }

    public int getScore() {
        return this.score;
    }

    public void playVillagers(int count) throws NotEnoughPiecesException {
        if (this.villagerCount < count) {
            String errorMessage = String.format("You do not have enough Villagers");
            throw new NotEnoughPiecesException(errorMessage);
        }

        this.villagerCount -= count;
    }

    public void playATotoro() throws NotEnoughPiecesException {
        if (this.totoroCount == 0) {
            String errorMessage = String.format("You do not have enough Totoros.");
            throw new NotEnoughPiecesException(errorMessage);
        }

        this.totoroCount -= 1;
    }

    public void playATiger() throws NotEnoughPiecesException {
        if (this.tigerCount == 0) {
            String errorMessage = String.format("You do not have enough Tigers.");
            throw new NotEnoughPiecesException(errorMessage);
        }

        this.totoroCount += 1;
    }

    public void foundSettlement(World existingWorld) throws
            NotEnoughPiecesException,
            NoPlayableHexException
    {
        try {
            this.playVillagers(1);
            this.settlementManager.foundSettlement(existingWorld);
            this.score += FOUND_SETTLEMENT_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoPlayableHexException e) {
            throw new NoPlayableHexException(e.getMessage());
        }
    }

    public void expandSettlement(World existingWorld) throws
            NotEnoughPiecesException,
            NoHexesToExpandToException
    {
        try {
            int numberOfVillagersRequiredToExpand = this.settlementManager.getNumberOfVillagersRequiredToExpand(existingWorld, Terrain.GRASSLANDS);
            this.playVillagers(numberOfVillagersRequiredToExpand);
            this.settlementManager.expandSettlement(existingWorld, Terrain.GRASSLANDS);
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            System.out.println(e.getMessage());
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoHexesToExpandToException e) {
            throw new NoHexesToExpandToException(e.getMessage());
        }
    }

    public void mergeSettlements() {
        this.settlementManager.mergeSettlements();
    }

    public void buildTotoroSanctuary(World existingWorld) throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        try {
            this.playATotoro();
            this.settlementManager.buildTotoroSanctuary(existingWorld);
            this.score += BUILD_TOTORO_SANCTUARY_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoPlayableHexException e) {
            // increment totoro back to previous count
            throw new BuildConditionsNotMetException(e.getMessage());
        }
    }

    public void buildTigerPlayground(World existingWorld) throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        try {
            this.playATiger();
            this.settlementManager.buildTigerPlayground(existingWorld);
            this.score += BUILD_TIGER_PLAYGROUND_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoPlayableHexException e) {
            // increment tigers back to previous count
            throw new BuildConditionsNotMetException(e.getMessage());
        }
    }

    /*
    public boolean hasTotoroSanctuary(Hex hexToCheck) {
        Settlement checkingSettlement = this.getSettlementFromHex(hexToCheck);

        if (checkingSettlement != null) {
            return settlementManager.hasTotoroSanctuary(checkingSettlement);
        }

        return false;
    }

    public boolean hasTigerPlayground(Hex hexToCheck) {
        Settlement checkingSettlement = this.getSettlementFromHex(hexToCheck);

        if (checkingSettlement != null) {
            return settlementManager.hasTigerPlayground(checkingSettlement);
        }

        return false;
    }
    */
}