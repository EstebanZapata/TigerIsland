package game.settlements.exceptions;

public class SettlementHeightRequirementException extends BuildConditionsNotMetException {
    public SettlementHeightRequirementException(String errorMessage) {
        super(errorMessage);
    }
}