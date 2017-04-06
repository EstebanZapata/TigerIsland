package game.world.rules.exceptions;

public class NoHexAtLocationException extends TilePlacementException {
    public NoHexAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
