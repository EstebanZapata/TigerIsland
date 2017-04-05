package game.world.exceptions;


public class TopVolcanoDoesNotCoverBottomVolcanoException  extends TilePlacementException{
    public TopVolcanoDoesNotCoverBottomVolcanoException(String errorMessage) {
        super(errorMessage);
    }
}
