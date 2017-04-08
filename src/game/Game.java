package game;


import game.player.exceptions.NotEnoughPiecesException;
import game.settlements.Settlement;
import game.settlements.exceptions.BuildConditionsNotMetException;
import game.settlements.exceptions.SettlementCannotBeExpandedException;
import game.world.*;
import game.player.*;
import game.world.rules.exceptions.IllegalTilePlacementException;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.tile.*;

import java.util.ArrayList;

public class Game {
    private static final int START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT = 1;

    public World world;
    public Player player1;
    public Player player2;

    public Game() {
        this.world = new World();
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void play() {
        boolean player1Turn = false;
        boolean tilePlaced = false;

        while (true) {
            Tile tile = drawTile(); // need to add first game.tile condition

            if (player1Turn) {
                while (!tilePlaced) {
                    try {
                        world.placeFirstTile(); //****** see above
                        tilePlaced = true;
                    } catch (IllegalTilePlacementException e) {
                        System.out.println(e.getMessage());
                    }
                }

                tilePlaced = false;

                // player 1 chooses a build action
                //if cannot do any, break from while loop and set flag
                try {
                    foundSettlement(player1);
                } catch (BuildConditionsNotMetException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                while (!tilePlaced) {
                    try {
                        world.placeFirstTile(); //******
                        tilePlaced = true;
                    } catch (IllegalTilePlacementException e) {
                        System.out.println(e.getMessage());
                    }
                }

                tilePlaced = false;

                // player 2 chooses a build action
                    //if cannot do any, break from while loop and set flag
            }

            // merge settlements

            // check end of game conditions

            player1Turn = !player1Turn;
        }

        // check failed build action flag --> determine winner from turn
    }


    public void foundSettlement(Player currentlyActivePlayer) throws BuildConditionsNotMetException {
        Hex foundingSettlementHex = null;

        try {
            currentlyActivePlayer.checkVillagerCount(1);
        } catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Hex> worldHexes = world.getAllHexesInWorld();

        // for now, choose first possible hex that meets conditions
        for (Hex worldHex : worldHexes) {
            if (checkSettlementHexConditions(worldHex, player1, player2)) {
                foundingSettlementHex = worldHex;
                break;
            }
        }

        if (foundingSettlementHex == null) {
            String errorMessage = String.format("There are no hexes suitable for founding a settlement.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        currentlyActivePlayer.foundSettlement(foundingSettlementHex);
        currentlyActivePlayer.playVillagers(1);
        currentlyActivePlayer.incrementScore(Player.FOUND_SETTLEMENT_POINTS);
    }

    private boolean checkSettlementHexConditions(Hex hexBeingChecked, Player player1, Player player2) {
        if (hexBeingChecked.getTerrain() == Terrain.VOLCANO) {
            return false;
        }

        if (hexBeingChecked.getHeight() != START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT) {
            return false;
        }

        if (player1.hasSettlementOnHex(hexBeingChecked) || (player2.hasSettlementOnHex(hexBeingChecked))) {
            return false;
        }

        return true;
    }

    public void expandSettlement(Player currentlyActivePlayer)
            throws SettlementCannotBeExpandedException
    {
        Terrain terrainType = chooseTerrainType();
        Settlement existingSettlement = chooseSettlementToExpand(currentlyActivePlayer, terrainType);
        ArrayList<Hex> hexesToExpandTo = getHexesToExpandToFromExistingSettlement(existingSettlement, terrainType);

        if (hexesToExpandTo == null) {
            String errorMessage = String.format("The settlement cannot be expanded.");
            throw new SettlementCannotBeExpandedException(errorMessage);
        }

        int numVillagersNeeded = getNumberOfVillagersRequiredToExpand(hexesToExpandTo);

        try {
            currentlyActivePlayer.checkVillagerCount(numVillagersNeeded);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }

        currentlyActivePlayer.expandSettlement(existingSettlement, hexesToExpandTo);
        currentlyActivePlayer.playVillagers(numVillagersNeeded);
        currentlyActivePlayer.incrementScore(numVillagersNeeded);
    }

    // AI Decision-Making
    private Terrain chooseTerrainType() {
        return Terrain.GRASSLANDS;
    }

    // AI Decision-Making
    private Settlement chooseSettlementToExpand(Player currentlyActivePlayer, Terrain terrainType) {
        int maxPossibleExpansionHexes = 0;
        ArrayList<Settlement> existingSettlements = currentlyActivePlayer.getSettlements();

       /* for (Settlement existingSettlement : existingSettlements) {
            ArrayList<Hex> jungleExpansionHexes = getHexesToExpandToFromExistingSettlement(existingSettlement, Terrain.JUNGLE);
            ArrayList<Hex> grasslandsExpansionHexes = getHexesToExpandToFromExistingSettlement(existingSettlement, Terrain.GRASSLANDS);
            ArrayList<Hex> lakeExpansionHexes = getHexesToExpandToFromExistingSettlement(existingSettlement, Terrain.LAKE);
            ArrayList<Hex> rockyExpansionHexes = getHexesToExpandToFromExistingSettlement(existingSettlement, Terrain.ROCKY);

            int max1 = Math.max(jungleExpansionHexes.size(), grasslandsExpansionHexes.size());
            int max2 = Math.max(lakeExpansionHexes.size(), rockyExpansionHexes.size());
            int max = Math.max(max1, max2);
            maxPossibleExpansionHexes = Math.max(max, maxPossibleExpansionHexes);
        }*/
       return existingSettlements.get(0);
    }

    private int getNumberOfVillagersRequiredToExpand(ArrayList<Hex> hexesToExpandTo) {
        int numVillagers = 0;

        for (Hex hex : hexesToExpandTo) {
            int hexHeight = hex.getHeight();
            numVillagers += hexHeight;
        }

        return numVillagers;
    }

    private ArrayList<Hex> getHexesToExpandToFromExistingSettlement(Settlement existingSettlement, Terrain terrainType) {
        if (terrainType == Terrain.VOLCANO) {
            return null;
        }

        ArrayList<Hex> potentialSettlementHexes = new ArrayList<Hex>();
        ArrayList<Hex> settlementHexes = existingSettlement.getHexesFromSettlement();

        for (Hex hex : settlementHexes) {
            Location hexLocation = hex.getLocation();
            Location[] hexLocationsAdjacentToCenter = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(hexLocation);

            for (Location adjacentHexLocation : hexLocationsAdjacentToCenter) {
                try {
                    Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                    if (!potentialSettlementHexes.contains(adjacentHex)) {
                        if (checkExpansionHexConditions(adjacentHex, terrainType, player1, player2)) {
                            potentialSettlementHexes.add(adjacentHex);
                        }
                    }
                }
                catch (NoHexAtLocationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return potentialSettlementHexes;
    }

    private boolean checkExpansionHexConditions(Hex hexBeingChecked, Terrain terrainType, Player player1, Player player2) {
        if (hexBeingChecked.getTerrain() != terrainType) {
            return false;
        }

        else if (player1.hasSettlementOnHex(hexBeingChecked) || (player2.hasSettlementOnHex(hexBeingChecked))) {
            return false;
        }

        return true;
    }

    public void buildTotoroSanctuary(Player currentlyActivePlayer) throws BuildConditionsNotMetException  {
        Settlement adjacentSettlement = null;

        try {
            currentlyActivePlayer.checkTotoroCount();
        } catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }

        Hex sanctuaryHex = chooseNewSanctuaryHex(currentlyActivePlayer);

        if (sanctuaryHex == null) {
            String errorMessage = String.format("There are no hexes suitable for founding a sanctuary.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        if (!checkSanctuaryHexConditions(sanctuaryHex, player1, player2)) {
            String errorMessage = String.format("The hex is not suitable for founding a sanctuary.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        Location sanctuaryHexLocation = sanctuaryHex.getLocation();
        Location[] adjacentHexLocationsToSanctuaryHex = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(sanctuaryHexLocation);
        ArrayList<Settlement> settlements = currentlyActivePlayer.getSettlements();

        for (Location adjacentHexLocation : adjacentHexLocationsToSanctuaryHex) {
            try {
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                if (currentlyActivePlayer.hasSettlementOnHex(adjacentHex)) {
                    for (Settlement settlement : settlements) {
                        if (checkSanctuarySettlementConditions(settlement)) {
                            ArrayList<Hex> hexes = settlement.getHexesFromSettlement();
                            for (Hex hex : hexes) {
                                if (hex == adjacentHex) {
                                    adjacentSettlement = settlement;
                                }
                            }
                        }
                    }
                }
            } catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
        }

        if (adjacentSettlement == null) {
            String errorMessage = String.format("The hex is not adjacent to an appropriate settlement.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        currentlyActivePlayer.buildTotoroSanctuary(adjacentSettlement, sanctuaryHex);
        currentlyActivePlayer.playATotoro();
        currentlyActivePlayer.incrementScore(Player.BUILD_SANCTUARY_POINTS);
    }

    // AI
    private Hex chooseNewSanctuaryHex(Player currentlyActivePlayer) {
        ArrayList<Settlement> existingSettlements = currentlyActivePlayer.getSettlements();

        for (Settlement existingSettlement : existingSettlements) {
            if (checkSanctuarySettlementConditions(existingSettlement)) {
                ArrayList<Hex> existingSettlementHexes = existingSettlement.getHexesFromSettlement();

                for (Hex existingSettlementHex : existingSettlementHexes) {
                    Location existingHexLocation = existingSettlementHex.getLocation();
                    Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(existingHexLocation);

                    for (Location adjacentHexLocation : adjacentHexLocations) {
                        try{
                            Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                            if (checkSanctuaryHexConditions(adjacentHex, player1, player2)) {
                                return adjacentHex;
                            }
                        } catch (NoHexAtLocationException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean checkSanctuarySettlementConditions(Settlement existingSettlement) {
        if (existingSettlement.getSettlementSize() < 5) {
            return false;
        }

        else if (existingSettlement.hasTotoroSanctuary()) {
            return false;
        }

        return true;
    }

    private boolean checkSanctuaryHexConditions(Hex hexBeingChecked, Player player1, Player player2) {
        if (hexBeingChecked.getTerrain() == Terrain.VOLCANO) {
            return false;
        }

        else if (player1.hasSettlementOnHex(hexBeingChecked) || (player2.hasSettlementOnHex(hexBeingChecked))) {
            return false;
        }

        return true;
    }

    public void buildTigerPlayground(Player currentlyActivePlayer) throws BuildConditionsNotMetException {
        Settlement adjacentSettlement = null;

        try {
            currentlyActivePlayer.checkTigerCount();
        } catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }

        Hex playgroundHex = chooseNewPlaygroundHex(currentlyActivePlayer);

        if (playgroundHex == null) {
            String errorMessage = String.format("There are no hexes suitable for founding a playground.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        if (!checkPlaygroundHexConditions(playgroundHex, player1, player2)) {
            String errorMessage = String.format("The hex is not suitable for founding a playground.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        Location playgroundHexLocation = playgroundHex.getLocation();
        Location[] adjacentHexLocationsToPlaygroundHex = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(playgroundHexLocation);
        ArrayList<Settlement> settlements = currentlyActivePlayer.getSettlements();

        for (Location adjacentHexLocation : adjacentHexLocationsToPlaygroundHex) {
            try {
                Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                if (currentlyActivePlayer.hasSettlementOnHex(adjacentHex)) {
                    for (Settlement settlement : settlements) {
                        if (checkPlaygroundSettlementConditions(settlement)) {
                            ArrayList<Hex> hexes = settlement.getHexesFromSettlement();
                            for (Hex hex : hexes) {
                                if (hex == adjacentHex) {
                                    adjacentSettlement = settlement;
                                }
                            }
                        }
                    }
                }
            } catch (NoHexAtLocationException e) {
                System.out.println(e.getMessage());
            }
        }

        if (adjacentSettlement == null) {
            String errorMessage = String.format("The hex is not adjacent to an appropriate settlement.");
            throw new BuildConditionsNotMetException(errorMessage);
        }

        currentlyActivePlayer.buildTigerPlayground(adjacentSettlement, playgroundHex);
        currentlyActivePlayer.playATiger();
        currentlyActivePlayer.incrementScore(Player.BUILD_PLAYGROUND_POINTS);
    }

    private Hex chooseNewPlaygroundHex(Player currentlyActivePlayer) {
        ArrayList<Settlement> existingSettlements = currentlyActivePlayer.getSettlements();
        Hex playgroundHex = null;

        for (Settlement existingSettlement : existingSettlements) {
            if (checkPlaygroundSettlementConditions(existingSettlement)) {
                ArrayList<Hex> existingSettlementHexes = existingSettlement.getHexesFromSettlement();

                for (Hex existingSettlementHex : existingSettlementHexes) {
                    Location existingHexLocation = existingSettlementHex.getLocation();
                    Location[] adjacentHexLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(existingHexLocation);

                    for (Location adjacentHexLocation : adjacentHexLocations) {
                        try{
                            Hex adjacentHex = world.getHexByLocation(adjacentHexLocation);
                            if (checkPlaygroundHexConditions(adjacentHex, player1, player2)) {
                                return adjacentHex;
                            }
                        } catch (NoHexAtLocationException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        return playgroundHex;
    }

    private boolean checkPlaygroundHexConditions(Hex hexBeingChecked, Player player1, Player player2) {
        int hexHeight = hexBeingChecked.getHeight();

        if (hexBeingChecked.getTerrain() == Terrain.VOLCANO) {
            return false;
        }

        else if (hexHeight < 3) {
            return false;
        }

        else if (player1.hasSettlementOnHex(hexBeingChecked) || (player2.hasSettlementOnHex(hexBeingChecked))) {
            return false;
        }

        return true;
    }

    private boolean checkPlaygroundSettlementConditions(Settlement existingSettlement) {
        return !(existingSettlement.hasTigerPlayground());
    }
}
