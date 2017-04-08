package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class SettlementDoesNotMeetConditionsException extends Exception {
    public SettlementDoesNotMeetConditionsException(String errorMessage) {
        super(errorMessage);
    }
}