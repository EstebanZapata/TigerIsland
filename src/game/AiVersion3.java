package game;

import game.player.*;
import game.settlements.*;
import game.settlements.exceptions.NoHexesToExpandToException;
import game.settlements.exceptions.SettlementCannotBeBuiltOnVolcanoException;
import game.tile.*;
import game.tile.orientation.TileOrientation;
import game.world.*;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.world.rules.exceptions.NoValidTileOrientationException;
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

        Location locationOnWhichToPlaceTile;
        TileOrientation tileOrientationToPlace;
        boolean tileDecisionHasNotBeenMade = true;

        if (iShouldAttemptToNuke()) {
            Settlement settlementToNuke = opponent.getLargestSettlementNotContainingATotoro();
            Hex[] potentiallyNukableVolcanoes = getPotentiallyNukableVolcanoes(settlementToNuke);
            final boolean nukableVolcanoesExist = potentiallyNukableVolcanoes.length != 0;
            if (nukableVolcanoesExist) {
                for (Hex volcanoToNuke : potentiallyNukableVolcanoes)
                    try {
                        tileOrientationToPlace = getNukeOrientation(settlementToNuke, volcanoToNuke);
                        locationOnWhichToPlaceTile = volcanoToNuke.getLocation();
                        tileDecisionHasNotBeenMade = false;
                        break;
                    } catch (NoValidTileOrientationException e) {
                        continue;
                    }
            }
        }
        //if(tileDecisionHasNotBeenMade && iShouldPlaceATileInOrderToExpand(firstTerrainType, secondTerrainType)) {
        //    locationOnWhichToPlaceTile = determineWhereToPlaceExpansionTile(firstTerrainType, secondTerrainType);
        //}
        if(tileDecisionHasNotBeenMade) {
            //Attempt to place a tile near one of my settlements w/o nuking it
            Settlement settlementToPlaceTileNear = theAI.getLargestSettlementNotContainingATotoro();
            Hex[] volcanoesToTryToBuildOn = getPotentiallyNukableVolcanoes(settlementToPlaceTileNear);
            final boolean volcanoesICanBuildOnExist = volcanoesToTryToBuildOn.length != 0;
            if(volcanoesICanBuildOnExist) {
                for (Hex volcanoToBuildOn : volcanoesToTryToBuildOn) {
                    //TODO: Get new function from Esteban.

                }

            }
        }

        return null;
        // return new GameActionMessage(gameId, moveNumber, myPlayerId, tileToPlace, );

    }

    private TileOrientation getNukeOrientation(Settlement settlementToNuke, Hex potentiallyNukableVolcano) throws NoValidTileOrientationException {
            for(Hex hexInSettlement : settlementToNuke.getHexesFromSettlement())
                world.calculateTileOrientationToCoverVolcanoLocationAndAdjacentLocation(potentiallyNukableVolcano.getLocation(), hexInSettlement.getLocation());

    }

    private boolean iShouldPlaceATileInOrderToExpand(Terrain firstTerrainType, Terrain secondTerrainType) {
        Settlement settlementToExpand = theAI.getLargestSettlementNotContainingATotoro();
        final int sizeOfSettlementToExpand = settlementToExpand.getSettlementSize();
        final int SIZE_REQUIRED_FOR_TOTORO = 5;
        if(sizeOfSettlementToExpand >= SIZE_REQUIRED_FOR_TOTORO)
            return false;

        int numberOfExpandableHexesForType1 = 0, numberOfExpandableHexesForType2 = 0;
        numberOfExpandableHexesForType1 = settlementToExpand.getNumberOfExpandableHexes(world, firstTerrainType);
        numberOfExpandableHexesForType2 = (firstTerrainType == secondTerrainType ? numberOfExpandableHexesForType1 : settlementToExpand.getNumberOfExpandableHexes(world, secondTerrainType));
        if(numberOfExpandableHexesForType1 == 0 || numberOfExpandableHexesForType2 == 0)
            return false;

        SettlementManager AiSettlementManager = theAI.settlementManager;
        int villagersRequiredForType1 = AiSettlementManager.getNumberOfVillagersRequiredToExpand(settlementToExpand, firstTerrainType);
        int villagersRequiredForType2 = (firstTerrainType == secondTerrainType ? villagersRequiredForType1 : AiSettlementManager.getNumberOfVillagersRequiredToExpand(settlementToExpand, secondTerrainType));
        if(villagersRequiredForType1 == 0 && villagersRequiredForType2 == 0)
            return false;

        final int MINIMUM_REMAINING_VILLAGERS_FOR_SAFE_PLACEMENT = 3;
        int availableVillagers = theAI.getVillagerCount();

        boolean canExpandOntoType1 = availableVillagers - villagersRequiredForType1 > MINIMUM_REMAINING_VILLAGERS_FOR_SAFE_PLACEMENT;
        boolean canExpandOntoType2 = (firstTerrainType == secondTerrainType ? canExpandOntoType1 : availableVillagers - villagersRequiredForType2 > MINIMUM_REMAINING_VILLAGERS_FOR_SAFE_PLACEMENT);

        return canExpandOntoType1 || canExpandOntoType2;
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

        if(settlementToBeNuked.getSettlementSize() >= MINIMUM_SIZE_TO_ATTEMPT_NUKE)
            return true;
        else
            return false;
    }
}
