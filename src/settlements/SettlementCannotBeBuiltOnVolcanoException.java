package settlements;

/**
 * Created by thomasbaldwin on 4/5/17.
 */

public class SettlementCannotBeBuiltOnVolcanoException extends Exception {
    SettlementCannotBeBuiltOnVolcanoException(String errorMessage) {
        super(errorMessage);
    }
}