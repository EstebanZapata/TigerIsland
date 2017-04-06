package game.world.rules.exceptions;

public class TileCompletelyOverlapsAnotherException extends IllegalTilePlacementException {
    public TileCompletelyOverlapsAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
