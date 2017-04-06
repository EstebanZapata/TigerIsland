package game.world.exceptions;

public class HexAlreadyAtLocationException extends TilePlacementException {
    public HexAlreadyAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
