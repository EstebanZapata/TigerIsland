package game.world.rules.exceptions;

public class SettlementWouldBeDestroyedException extends IllegalTilePlacementException {
    public SettlementWouldBeDestroyedException(String s) {
        super(s);
    }
}
