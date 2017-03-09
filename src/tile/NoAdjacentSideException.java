package tile;


public class NoAdjacentSideException extends Throwable {
    NoAdjacentSideException(String message) {
        super(message);
    }
}
