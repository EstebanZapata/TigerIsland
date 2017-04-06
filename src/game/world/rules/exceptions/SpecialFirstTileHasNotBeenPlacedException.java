package game.world.rules.exceptions;

public class SpecialFirstTileHasNotBeenPlacedException extends IllegalTilePlacementException {
    public SpecialFirstTileHasNotBeenPlacedException(String errorMessage) {
        super(errorMessage);
    }
}
