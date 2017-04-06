package game.world.exceptions;

public class NoHexAtLocationException extends TilePlacementException {
    public NoHexAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
