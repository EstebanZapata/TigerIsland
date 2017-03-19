package game;

public class NoHexAtLocationException extends Exception {
    public NoHexAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
