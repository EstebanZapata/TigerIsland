package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class SettlementAlreadyExistsOnHexException extends BuildConditionsNotMetException {
    public SettlementAlreadyExistsOnHexException(String errorMessage) {
        super(errorMessage);
    }
}