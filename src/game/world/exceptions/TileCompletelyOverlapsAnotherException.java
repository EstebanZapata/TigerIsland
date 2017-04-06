package game.world.exceptions;

public class TileCompletelyOverlapsAnotherException extends TilePlacementException {
    public TileCompletelyOverlapsAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
