package io;

import game.Game;
import game.tile.Location;
import game.tile.orientation.TileOrientation;
import thread.*;
import game.tile.Terrain;
import game.tile.Tile;

import static game.tile.Terrain.*;

public class MessageParser {
    private static String tournamentPassword = "<tournament_password>";
    private static String userName = "<userName>";
    private static String userPassword = "<password>";

    public static Message parseServerInputAndComposeAction(String serverInput){
        String parts[] = serverInput.split(" ");

        Message actionToTake = null;

        if (serverInput.contains(ServerStrings.WELCOME)){
            actionToTake = new ClientMessage(ClientStrings.ENTER + tournamentPassword);
        }
        else if (serverInput.contains(ServerStrings.TWO_SHALL_ENTER)){
            actionToTake = new ClientMessage(ClientStrings.I_AM + userName + " " + userPassword);
        }
        else if (serverInput.contains(ServerStrings.WAIT_FOR_THE_TOURNAMENT)){
            String ourPlayerId = parts[6];

            actionToTake = new PlayerIdMessage(ourPlayerId);
        }
        else if (serverInput.contains(ServerStrings.NEW_CHALLENGE)){

            actionToTake = Message.EMPTY;
        }
        else if (serverInput.contains(ServerStrings.BEGIN_ROUND)){

            actionToTake = Message.EMPTY;
        }

        else if (serverInput.contains(ServerStrings.NEW_MATCH)){
            String opponentId = parts[parts.length-1];

            actionToTake = new NewMatchMessage(opponentId);
        }
        else if (serverInput.contains(ServerStrings.MAKE_YOUR_MOVE)){
            String gameId = parts[5];
            double moveTime = Double.parseDouble(parts[7]);
            String tileString = parts[parts.length-1];

            Tile tileToPlace = getTileFromServerTerrain(tileString);

            actionToTake = new GameCommandMessage(gameId, moveTime, tileToPlace);
        }

        else if (parts[0].equals("GAME") && parts[2].equals("MOVE")){
            if(parts[6].equals("FORFEITED") || parts[6].equals("LOST")) {
                String gameId = parts[1];

                actionToTake = new GameEndMessage(gameId);
            }
            else {


                actionToTake = composeGameActionMessage(parts);
            }

        }
        else if (parts[0].equals("GAME") && parts[2].equals("OVER")){
            String gameIdToEnd = parts[1];

            actionToTake = new GameEndMessage(gameIdToEnd);
        }
        else {
            actionToTake = Message.EMPTY;
        }
        
      return actionToTake;
    }

    private static Tile getTileFromServerTerrain(String tile) {
        String terrain[] = tile.split("\\+");
        Terrain terrainZero = stringToTerrain(terrain[0]);
        Terrain terrainOne = stringToTerrain(terrain[1]);
        Tile tileToBePlaced = new Tile(terrainOne,terrainZero);

        return tileToBePlaced;
    }

    private static GameActionMessage composeGameActionMessage(String parts[]) {
        String playerId = parts[5];
        String tileString = parts[7];
        Tile tilePlaced = getTileFromServerTerrain(tileString);

        int x = Integer.parseInt(parts[9]);
        int y = Integer.parseInt(parts[10]);
        int z = Integer.parseInt(parts[11]);

        Location locationOfVolcano = new Location(x,y,z);

        int intOrientation = Integer.parseInt(parts[12]);
        TileOrientation tileOrientation = TileOrientation.getOrientationFromServerOrientation(intOrientation);

        BuildAction buildAction = null;

        int buildX = 0;
        int buildY = 0;
        int buildZ = 0;

        Terrain terrainToExpandOnto = null;

        if (parts[13].equals("FOUND")) {
            buildAction = BuildAction.FOUNDED_SETTLEMENT;

            buildX = Integer.parseInt(parts[16]);
            buildY = Integer.parseInt(parts[17]);
            buildZ = Integer.parseInt(parts[18]);
        }
        else if(parts[13].equals("EXPAND")) {
            buildAction = BuildAction.EXPANDED_SETTLEMENT;

            buildX = Integer.parseInt(parts[16]);
            buildY = Integer.parseInt(parts[17]);
            buildZ = Integer.parseInt(parts[18]);

            terrainToExpandOnto = stringToTerrain(parts[19]);
        }
        else if(parts[13].equals("BUILD") && parts[14].equals("TOTORO")) {
            buildAction = BuildAction.BUILT_TOTORO_SANCTUARY;

            buildX = Integer.parseInt(parts[17]);
            buildY = Integer.parseInt(parts[18]);
            buildZ = Integer.parseInt(parts[19]);
        }
        else if(parts[13].equals("BUILD") && parts[14].equals("TIGER")) {
            buildAction = BuildAction.BUILT_TIGER_PLAYGROUND;

            buildX = Integer.parseInt(parts[17]);
            buildY = Integer.parseInt(parts[18]);
            buildZ = Integer.parseInt(parts[19]);
        }
        else if(parts[13].equals("UNABLE")) {
            buildAction = BuildAction.UNABLE_TO_BUILD;
        }

        Location locationOfBuildAction = new Location(buildX, buildY, buildZ);

        GameActionMessage actionToTake = new GameActionMessage(playerId, tilePlaced, locationOfVolcano, tileOrientation, buildAction,
                locationOfBuildAction, terrainToExpandOnto);

        return actionToTake;

    }

    private static Terrain stringToTerrain(String type) {
        if(type.equals("JUNGLE"))
            return JUNGLE;
        if(type.equals("LAKE"))
            return LAKE;
        if(type.equals("GRASS"))
            return GRASSLANDS;
        else//(type.equals("ROCK"))
            return ROCKY;
    }




}
