package game;

import pieces.PieceContainer;

public class Player {
    private static final int STARTING_SCORE_COUNT = 0;

    private int score;
    private PieceContainer bagOfPieces = new PieceContainer();
    public Player(){
        this.score = STARTING_SCORE_COUNT;
    };

    public int getScore() {
        return this.score;
    }

}
