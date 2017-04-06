package game.world.exceptions;

public class AirBelowTileException extends TilePlacementException {
    public AirBelowTileException(String errorMessage) {
        super(errorMessage);
    }
}
