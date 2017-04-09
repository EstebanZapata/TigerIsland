package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class HexDoesNotMeetConditionsException extends Exception {
    public HexDoesNotMeetConditionsException(String errorMessage) {
        super(errorMessage);
    }
}
