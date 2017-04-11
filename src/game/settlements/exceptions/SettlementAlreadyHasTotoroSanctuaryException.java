package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/9/17.
 */

public class SettlementAlreadyHasTotoroSanctuaryException extends BuildConditionsNotMetException {
    public SettlementAlreadyHasTotoroSanctuaryException(String errorMessage) {
        super(errorMessage);
    }
}