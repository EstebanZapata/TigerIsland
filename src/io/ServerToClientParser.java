package io;

import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.orientation.TileOrientation;
import game.tile.Terrain;
import game.tile.Tile;
import thread.message.*;

import static game.tile.Terrain.*;

public class ServerToClientParser {
    private static String tournamentPassword = "<tournament_password>";
    private static String userName = "<userName>";
    private static String userPassword = "<password>";

    public static Message parseServerInputAndComposeMessage(String serverInput){
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

            actionToTake = Message.NO_ACTION;
        }
        else if (serverInput.contains(ServerStrings.BEGIN_ROUND)){

            actionToTake = Message.NO_ACTION;
        }

        else if (serverInput.contains(ServerStrings.NEW_MATCH)){
            String opponentId = parts[parts.length-1];

            actionToTake = new NewMatchMessage(opponentId);
        }
        else if (serverInput.contains(ServerStrings.MAKE_YOUR_MOVE)){
            String gameId = parts[5];
            double moveTime = Double.parseDouble(parts[7]);
            int moveNumber = Integer.parseInt(parts[10]);
            String tileString = parts[parts.length-1];

            Tile tileToPlace = getTileFromServerTerrain(tileString);

            actionToTake = new GameCommandMessage(gameId, moveTime, moveNumber, tileToPlace);
        }

        else if (parts[0].contains("GAME") && parts[2].contains("MOVE")){
            if(parts[6].contains("FORFEITED") || parts[6].contains("LOST")) {
                String gameId = parts[1];

                actionToTake = new GameEndMessage(gameId);
            }
            else {
                actionToTake = composeGameActionMessage(parts);
            }

        }
        else if (parts[0].contains("GAME") && parts[2].contains("OVER")){
            String gameIdToEnd = parts[1];

            actionToTake = new GameEndMessage(gameIdToEnd);
        }
        else if (serverInput.contains(ServerStrings.THANK_YOU_FOR_PLAYING)) {
            actionToTake = new DisconnectMessage();

        }
        else {
            actionToTake = Message.NO_ACTION;
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
        String gameId = parts[1];
        int moveNumber = Integer.parseInt(parts[3]);
        String playerId = parts[5];

        String tileString = parts[7];
        Tile tilePlaced = getTileFromServerTerrain(tileString);

        String serverX = parts[9];
        String serverY = parts[10];
        String serverZ = parts[11];

        Location locationOfVolcano = convertServerCoordinatesToClientLocation(serverX, serverY, serverZ);

        int intOrientation = Integer.parseInt(parts[12]);
        TileOrientation tileOrientation = TileOrientation.getOrientationFromServerOrientation(intOrientation);

        BuildAction buildAction = null;

        String serverBuildX = "0";
        String serverBuildY = "0";
        String serverBuildZ = "0";

        Terrain terrainToExpandOnto = null;

        if (parts[13].contains("FOUND")) {
            buildAction = BuildAction.FOUNDED_SETTLEMENT;

            serverBuildX = parts[16];
            serverBuildY = parts[17];
            serverBuildZ = parts[18];
        }
        else if(parts[13].contains("EXPAND")) {
            buildAction = BuildAction.EXPANDED_SETTLEMENT;

            serverBuildX = parts[16];
            serverBuildY = parts[17];
            serverBuildZ = parts[18];

            terrainToExpandOnto = stringToTerrain(parts[19]);
        }
        else if(parts[13].contains("BUILD") && parts[14].contains("TOTORO")) {
            buildAction = BuildAction.BUILT_TOTORO_SANCTUARY;

            serverBuildX = parts[17];
            serverBuildY = parts[18];
            serverBuildZ = parts[19];
        }
        else if(parts[13].contains("BUILD") && parts[14].contains("TIGER")) {
            buildAction = BuildAction.BUILT_TIGER_PLAYGROUND;

            serverBuildX = parts[17];
            serverBuildY = parts[18];
            serverBuildZ = parts[19];
        }
        else if(parts[13].contains("UNABLE")) {
            buildAction = BuildAction.UNABLE_TO_BUILD;
        }

        Location locationOfBuildAction = convertServerCoordinatesToClientLocation(serverBuildX, serverBuildY, serverBuildZ);

        GameActionMessage actionToTake
                = new GameActionMessage(gameId, moveNumber, playerId, tilePlaced, locationOfVolcano, tileOrientation,
                buildAction, locationOfBuildAction, terrainToExpandOnto);

        return actionToTake;

    }

    private static Terrain stringToTerrain(String type) {
        if(type.contains("JUNGLE"))
            return JUNGLE;
        if(type.contains("LAKE"))
            return LAKE;
        if(type.contains("GRASS"))
            return GRASSLANDS;
        else
            return ROCKY;

    }

    public static Location convertServerCoordinatesToClientLocation(String x, String y, String z)
    {
        int clientX = Integer.parseInt(x);
        int clientY = Integer.parseInt(z) * -1;
        int clientZ = 0;

        return new Location(clientX, clientY, clientZ);
    }


}
