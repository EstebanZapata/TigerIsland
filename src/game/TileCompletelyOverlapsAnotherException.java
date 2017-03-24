package game;

public class TileCompletelyOverlapsAnotherException extends Exception {
    TileCompletelyOverlapsAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
