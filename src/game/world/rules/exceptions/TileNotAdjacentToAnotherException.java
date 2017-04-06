package game.world.rules.exceptions;

public class TileNotAdjacentToAnotherException extends TilePlacementException {
    public TileNotAdjacentToAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
