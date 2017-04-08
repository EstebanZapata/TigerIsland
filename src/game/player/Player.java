package game.player;

import game.player.exceptions.NotEnoughPiecesException;
import game.tile.*;
import game.settlements.*;

import java.util.ArrayList;

public class Player {
    public static final int FOUND_SETTLEMENT_POINTS = 1;
    public static final int BUILD_SANCTUARY_POINTS = 200;
    public static final int BUILD_PLAYGROUND_POINTS = 75;

    private static final int STARTING_SCORE_COUNT = 0;
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;

    private int score = STARTING_SCORE_COUNT;
    private int totoroCount = STARTING_TOTORO_COUNT;
    private int tigerCount = STARTING_TIGER_COUNT;
    private int villagerCount = STARTING_VILLAGER_COUNT;

    private String playerId;



    public SettlementManager settlementManager = new SettlementManager();

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int changes) {
        this.score += changes;
    }

    public void checkVillagerCount(int count) throws NotEnoughPiecesException {
        if (this.villagerCount < count) {
            String errorMessage = String.format("You do not have enough Villagers");
            throw new NotEnoughPiecesException(errorMessage);
        }
    }

    public void checkTotoroCount() throws NotEnoughPiecesException {
        if (this.totoroCount == 0) {
            String errorMessage = String.format("You do not have enough Totoro");
            throw new NotEnoughPiecesException(errorMessage);
        }
    }

    public void checkTigerCount() throws NotEnoughPiecesException {
        if (this.tigerCount == 0) {
            String errorMessage = String.format("You do not have enough Tigers");
            throw new NotEnoughPiecesException(errorMessage);
        }
    }

    public void playVillagers(int count) {
        villagerCount -= count;
    }

    public void playATotoro() {
        totoroCount--;
    }

    public void playATiger() {
        tigerCount--;
    }

    public void foundSettlement(Hex foundingHex) {
        settlementManager.foundSettlement(foundingHex);
    }

    public Boolean hasSettlementOnHex(Hex hexToSearchFor) {
        return settlementManager.hasSettlementOnHex(hexToSearchFor);
    }

    public void expandSettlement(Settlement existingSettlement, ArrayList<Hex> hexesToExpandTo) {
        settlementManager.expandSettlement(existingSettlement, hexesToExpandTo);
    }

    public ArrayList<Settlement> getSettlements() {
        return settlementManager.getSettlements();
    }

    public void buildTotoroSanctuary(Settlement existingSettlement, Hex sanctuaryHex) {
        settlementManager.buildTotoroSanctuary(existingSettlement, sanctuaryHex);
    }

    public void buildTigerPlayground(Settlement existingSettlement, Hex playgroundHex) {
        settlementManager.buildTigerPlayground(existingSettlement, playgroundHex);
    }

    public void mergeSettlements() {
        settlementManager.mergeSettlements();
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return this.playerId;
    }
}