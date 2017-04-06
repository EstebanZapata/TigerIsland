package game.world.rules.exceptions;

public class AirBelowTileException extends TilePlacementException {
    public AirBelowTileException(String errorMessage) {
        super(errorMessage);
    }
}
