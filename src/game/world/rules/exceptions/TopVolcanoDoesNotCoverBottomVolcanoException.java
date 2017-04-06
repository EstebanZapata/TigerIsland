package game.world.rules.exceptions;


public class TopVolcanoDoesNotCoverBottomVolcanoException  extends TilePlacementException{
    public TopVolcanoDoesNotCoverBottomVolcanoException(String errorMessage) {
        super(errorMessage);
    }
}
