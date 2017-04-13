package game;

import game.player.*;
import game.settlements.*;
import game.tile.*;
import game.world.*;
import game.world.rules.exceptions.NoHexAtLocationException;
import thread.message.GameActionMessage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Liam on 4/11/2017.
 */
public class AiVersion3 {
    private World world;
    private Player theAI, opponent;

    public AiVersion3(World world, Player opponent, Player theAI) {
        this.world = world;
        this.opponent = opponent;
        this.theAI = theAI;
    }

    public GameActionMessage chooseMove(String gameId, int moveNumber, String myPlayerId, Tile tileToPlace) {
        Terrain firstTerrainType, secondTerrainType;
        firstTerrainType = tileToPlace.getLeftHexTerrain();
        secondTerrainType = tileToPlace.getRightHexTerrain();
        if(iShouldAttemptToNuke()) {
            Settlement settlementToNuke = opponent.getLargestSettlementNotContainingATotoro();
            Hex[] potentiallyNukableVolcanoes = getPotentiallyNukableVolcanoes(settlementToNuke);
        }
        else if(iShouldPlaceATileInOrderToExpand(firstTerrainType, secondTerrainType)) {

        }


        return null;
        // return new GameActionMessage(gameId, moveNumber, myPlayerId, tileToPlace, );

    }

    private boolean iShouldPlaceATileInOrderToExpand(Terrain firstTerrainType, Terrain secondTerrainType) {
        Settlement settlementToExpand = theAI.getLargestSettlementNotContainingATotoro();
        //if(settlementToExpand.getSettlementSize() >= 5)
            return false;
        //else if(settlementToExpand)
    }

    private Hex[] getPotentiallyNukableVolcanoes(Settlement settlement) {
        Location[] locationsToCheck = settlement.getAllHexLocationsAdjacentToSettlement();
        Set<Hex> setOfNukableVolcanoes = new HashSet<>();
        for(Location location : locationsToCheck) {
            try {
                Hex hexToCheck = world.getHexByLocation(location);
                if(hexToCheck.getTerrain() == Terrain.VOLCANO)
                    setOfNukableVolcanoes.add(hexToCheck);

            } catch(NoHexAtLocationException e) {
                continue;
            }
        }
        return setOfNukableVolcanoes.toArray(new Hex[0]);
    }

    private boolean iShouldAttemptToNuke() {
        final int MINIMUM_SIZE_TO_ATTEMPT_NUKE = 4;

        Settlement settlementToBeNuked = opponent.getLargestSettlementNotContainingATotoro();

        if(settlementToBeNuked.getSettlementSize() > MINIMUM_SIZE_TO_ATTEMPT_NUKE)
            return true;
        else
            return false;
    }
}
