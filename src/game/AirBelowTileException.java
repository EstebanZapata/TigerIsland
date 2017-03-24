package game;

public class AirBelowTileException extends Exception {
    AirBelowTileException(String errorMessage) {
        super(errorMessage);
    }
}
