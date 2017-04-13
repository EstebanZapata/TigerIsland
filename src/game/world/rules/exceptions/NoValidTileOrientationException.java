package game.world.rules.exceptions;

public class NoValidTileOrientationException extends IllegalTilePlacementException {
    public NoValidTileOrientationException(String errorMessage) {
        super(errorMessage);
    }
}
