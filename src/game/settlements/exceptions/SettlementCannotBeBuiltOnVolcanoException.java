package game.settlements.exceptions;

public class SettlementCannotBeBuiltOnVolcanoException extends BuildConditionsNotMetException {
    public SettlementCannotBeBuiltOnVolcanoException(String errorMessage) {
        super(errorMessage);
    }
}