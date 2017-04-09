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

    public Player(World existingWorld) {
        this.score = Settings.STARTING_SCORE_COUNT;
        this.totoroCount = Settings.STARTING_TOTORO_COUNT;
        this.tigerCount = Settings.STARTING_TIGER_COUNT;
        this.villagerCount = Settings.STARTING_VILLAGER_COUNT;
        this.settlementManager = new SettlementManager(existingWorld);
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

        this.tigerCount -= 1;
    }

    public void foundSettlement(Hex settlementHex) throws
            NotEnoughPiecesException,
            SettlementAlreadyExistsOnHexException
    {
        try {
            this.useVillagers(1);
            this.settlementManager.foundSettlement(settlementHex);
            this.score += Settings.FOUND_SETTLEMENT_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (SettlementAlreadyExistsOnHexException e) {
            this.villagerCount += 1;
            throw new SettlementAlreadyExistsOnHexException(e.getMessage());
        }
    }

    public void expandSettlement(Settlement existingSettlement) throws
            NotEnoughPiecesException,
            NoHexesToExpandToException
    {
        try {
            int numberOfVillagersRequiredToExpand = this.settlementManager.getNumberOfVillagersRequiredToExpand(Terrain.GRASSLANDS);
            try {
                this.useVillagers(numberOfVillagersRequiredToExpand);
                this.settlementManager.expandSettlement(existingSettlement, Terrain.GRASSLANDS);
            }
            catch (NotEnoughPiecesException e) {
                throw new NotEnoughPiecesException(e.getMessage());
            }
            catch (NoHexesToExpandToException e) {
                this.villagerCount += numberOfVillagersRequiredToExpand;
                throw new NoHexesToExpandToException(e.getMessage());
            }
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {

        }
    }

    public void mergeSettlements(World existingWorld) {
        this.settlementManager.mergeSettlements();
    }

    public void buildTotoroSanctuary() throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        try {
            this.useTotoro();
            this.settlementManager.buildTotoroSanctuary();
            this.score += Settings.BUILD_TOTORO_SANCTUARY_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoPlayableHexException e) {
            this.totoroCount += 1;
            throw new BuildConditionsNotMetException(e.getMessage());
        }
    }

    public void buildTigerPlayground(Hex hexToBuildOn) throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        try {
            this.useTiger();
            this.settlementManager.buildTigerPlayground(hexToBuildOn);
            this.score += Settings.BUILD_TIGER_PLAYGROUND_POINTS;
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoSettlementOnAdjacentHexesException e) {
            this.tigerCount += 1;
            throw new BuildConditionsNotMetException(e.getMessage());
        }
    }
}