package game.world.rules.exceptions;

public class TileCompletelyOverlapsAnotherException extends TilePlacementException {
    public TileCompletelyOverlapsAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
