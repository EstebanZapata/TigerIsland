package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/9/17.
 */

public class SettlementAlreadyHasTigerPlaygroundException extends Exception {
    public SettlementAlreadyHasTigerPlaygroundException(String errorMessage) {
        super(errorMessage);
    }
}