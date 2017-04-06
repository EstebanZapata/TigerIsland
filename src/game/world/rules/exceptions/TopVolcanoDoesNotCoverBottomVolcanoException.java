package game.world.rules.exceptions;


public class TopVolcanoDoesNotCoverBottomVolcanoException  extends IllegalTilePlacementException {
    public TopVolcanoDoesNotCoverBottomVolcanoException(String errorMessage) {
        super(errorMessage);
    }
}
