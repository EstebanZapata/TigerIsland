package game.settlements.exceptions;

public class SettlementCannotBeCompletelyWipedOutException extends Exception {
    public SettlementCannotBeCompletelyWipedOutException(String errorMessage) {
        super(errorMessage);
    }
}