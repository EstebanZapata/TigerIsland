package game;

public class HexAlreadyAtLocationException extends Exception {
    public HexAlreadyAtLocationException(String errorMessage) {
        super(errorMessage);
    }
}
