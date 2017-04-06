package game.player.exceptions;

/**
 * Created by thomasbaldwin on 4/5/17.
 */

public class NotEnoughPiecesException extends Exception {
    NotEnoughPiecesException(String errorMessage) {
        super(errorMessage);
    }
}
