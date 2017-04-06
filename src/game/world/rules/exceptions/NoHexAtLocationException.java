package game.world.rules.exceptions;

public class NoHexAtLocationException extends IllegalTilePlacementException {
    public NoHexAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
