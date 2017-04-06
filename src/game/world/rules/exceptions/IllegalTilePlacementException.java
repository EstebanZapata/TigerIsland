package game.world.rules.exceptions;

public class IllegalTilePlacementException extends Exception {
    public IllegalTilePlacementException(String errorMessage) {
        super(errorMessage);
    }
}
