package game.world.rules.exceptions;

public class HexAlreadyAtLocationException extends TilePlacementException {
    public HexAlreadyAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
