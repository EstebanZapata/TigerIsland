package game.world.rules.exceptions;

public class SpecialFirstTileHasAlreadyBeenPlacedExeption extends IllegalTilePlacementException {
    public SpecialFirstTileHasAlreadyBeenPlacedExeption(String errorMessage) {
        super(errorMessage);
    }
}
