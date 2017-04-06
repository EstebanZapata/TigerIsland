package game.settlements.exceptions;

public class BuildConditionsNotMetException extends Exception {
    public BuildConditionsNotMetException(String errorMessage) {
        super(errorMessage);
    }
}