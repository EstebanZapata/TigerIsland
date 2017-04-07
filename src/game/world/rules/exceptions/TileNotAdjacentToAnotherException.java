package game.world.rules.exceptions;

public class TileNotAdjacentToAnotherException extends IllegalTilePlacementException {
    public TileNotAdjacentToAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
