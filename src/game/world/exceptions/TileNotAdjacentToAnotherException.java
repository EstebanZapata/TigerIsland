package game.world.exceptions;

public class TileNotAdjacentToAnotherException extends TilePlacementException {
    public TileNotAdjacentToAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
