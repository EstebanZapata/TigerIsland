package game.player;

import game.*;
import game.player.exceptions.*;
import game.settlements.*;
import game.settlements.exceptions.*;
import game.world.*;
import tile.*;

public class Player {
    private int score;
    private int totoroCount;
    private int tigerCount;
    private int villagerCount;

    public SettlementManager settlementManager;

    public Player() {
        this.score = Settings.STARTING_SCORE_COUNT;
        this.totoroCount = Settings.STARTING_TOTORO_COUNT;
        this.tigerCount = Settings.STARTING_TIGER_COUNT;
        this.villagerCount = Settings.STARTING_VILLAGER_COUNT;
        this.settlementManager = new SettlementManager();
    }

    public int getScore() {
        return this.score;
    }

    public void useVillagers(int count) throws NotEnoughPiecesException {
        if (this.villagerCount < count) {
            String errorMessage = String.format("You do not have enough Villagers");
            throw new NotEnoughPiecesException(errorMessage);
        }

        this.villagerCount -= count;
    }

    public void useTotoro() throws NotEnoughPiecesException {
        if (this.totoroCount == 0) {
            String errorMessage = String.format("You do not have enough Totoros.");
            throw new NotEnoughPiecesException(errorMessage);
        }

        this.totoroCount -= 1;
    }

    public void useTiger() throws NotEnoughPiecesException {
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
            this.useVillagers(1);
            this.settlementManager.foundSettlement(existingWorld);
            this.score += Settings.FOUND_SETTLEMENT_POINTS;
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
            this.useVillagers(numberOfVillagersRequiredToExpand);
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
            this.useTotoro();
            this.settlementManager.buildTotoroSanctuary(existingWorld);
            this.score += Settings.BUILD_TOTORO_SANCTUARY_POINTS;
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
            this.useTiger();
            this.settlementManager.buildTigerPlayground(existingWorld);
            this.score += Settings.BUILD_TIGER_PLAYGROUND_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoPlayableHexException e) {
            // increment tigers back to previous count
            throw new BuildConditionsNotMetException(e.getMessage());
        }
    }
}