package settlements;

/**
 * Created by thomasbaldwin on 4/5/17.
 */

public class SettlementIsOccupiedException extends Exception {
    SettlementIsOccupiedException(String errorMessage) {
        super(errorMessage);
    }
}
