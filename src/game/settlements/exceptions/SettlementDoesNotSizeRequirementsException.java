package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/9/17.
 */

public class SettlementDoesNotSizeRequirementsException extends Exception {
    public SettlementDoesNotSizeRequirementsException(String errorMessage) {
        super(errorMessage);
    }
}