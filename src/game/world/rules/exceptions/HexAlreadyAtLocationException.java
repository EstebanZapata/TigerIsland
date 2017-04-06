package game.world.rules.exceptions;

public class HexAlreadyAtLocationException extends IllegalTilePlacementException {
    public HexAlreadyAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
