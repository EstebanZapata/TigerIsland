package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class NoPlayableHexException extends Exception {
    public NoPlayableHexException(String errorMessage) {
        super(errorMessage);
    }
}

