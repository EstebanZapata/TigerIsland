package game;

public class TileNotAdjacentToAnotherException extends Exception {
    TileNotAdjacentToAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
