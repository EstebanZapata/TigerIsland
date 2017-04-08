package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class NoSettlementOnHexException extends Exception {
    public NoSettlementOnHexException(String errorMessage) {
        super(errorMessage);
    }
}
