package game.world.rules.exceptions;

public class AirBelowTileException extends IllegalTilePlacementException {
    public AirBelowTileException(String errorMessage) {
        super(errorMessage);
    }
}
