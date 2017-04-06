package game;

import pieces.NotEnoughPiecesException;
import tile.*;
import pieces.PieceContainer;
import settlements.*;

public class Player {
    private static final int STARTING_SCORE_COUNT = 0;
    private static final int FOUND_SETTLEMENT_POINTS = 1;

    private int score;
    private PieceContainer bagOfPieces;
    private SettlementManager settlementManager;

    public Player(){
        this.score = STARTING_SCORE_COUNT;
    };

    public int getScore() {
        return this.score;
    }

    public void foundSettlement(Hex hexToBuildOn) {
        try {
            this.bagOfPieces.playAVillager();
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
