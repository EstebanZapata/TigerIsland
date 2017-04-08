package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class NoHexesToExpandToException extends Exception {
    public NoHexesToExpandToException(String errorMessage) {
        super(errorMessage);
    }
}

