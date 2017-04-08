package io;

import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import thread.message.GameActionMessage;

public class ClientToServerParser {

    public static String getStringFromGameActionMessage(GameActionMessage gameActionMessage) {
        String stringFromGameActionMessage =
                String.format("GAME %s MOVE %d ", gameActionMessage.getGameId(), gameActionMessage.getMoveNumber());

        stringFromGameActionMessage += getTilePlacementString(gameActionMessage.getTilePlaced(),
                gameActionMessage.getLocationOfVolcano(), gameActionMessage.getTileOrientationPlaced());

        stringFromGameActionMessage +=
                getBuildActionString(gameActionMessage.getBuildActionPerformed(),
                        gameActionMessage.getLocationOfBuildAction(), gameActionMessage.getTerrainExpandedOnto());

        return stringFromGameActionMessage;
    }

    private static String getTilePlacementString(Tile tile, Location location, TileOrientation orientation) {
        String tileString =
                String.format("%s+%s", terrainToString(tile.getRightHexRelativeToVolcano().getTerrain()),
                        terrainToString(tile.getLeftHexRelativeToVolcano().getTerrain()));

        String serverLocation = convertClientCoordinatesToServerCoordinates(location);

        String tilePlacementString =
                String.format("PLACE %s AT %s %d ", tileString, serverLocation, orientation.getServerProtocolOrientation());

        return tilePlacementString;
    }

    private static String getBuildActionString(BuildAction buildAction, Location locationOfBuildAction, Terrain terrainExpandedTo) {
        String buildActionString = null;

        switch(buildAction) {
            case FOUNDED_SETTLEMENT:
                buildActionString = "FOUND SETTLEMENT AT ";
                break;
            case EXPANDED_SETTLEMENT:
                buildActionString = "EXPAND SETTLEMENT AT ";
                break;
            case BUILT_TOTORO_SANCTUARY:
                buildActionString = "BUILD TOTORO SANCTUARY AT ";
                break;
            case BUILT_TIGER_PLAYGROUND:
                buildActionString = "BUILD TIGER PLAYGROUND AT ";
                break;
            case UNABLE_TO_BUILD:
                buildActionString = "UNABLE TO BUILD";
                break;
        }

        if (buildActionString.equals("UNABLE TO BUILD")) {
            return buildActionString;
        }

        buildActionString += convertClientCoordinatesToServerCoordinates(locationOfBuildAction);

        if (buildActionString.contains("EXPAND")) {
            buildActionString += terrainToString(terrainExpandedTo);
        }

        return buildActionString;

    }


    private static String convertClientCoordinatesToServerCoordinates(Location locationToConvert) {
        int serverXCoordinate = locationToConvert.getxCoordinate();
        int serverZCoordinate = locationToConvert.getyCoordinate();
        int serverYCoordinate = -(serverXCoordinate + serverZCoordinate);
        String serverCoordinates = Integer.toString(serverXCoordinate) + ' ' + Integer.toString(serverYCoordinate) + ' ' + Integer.toString(serverZCoordinate);
        return serverCoordinates;
    }

    private static String terrainToString(Terrain terrain) {
        switch(terrain) {
            case GRASSLANDS:
                return "GRASS";
            case LAKE:
                return "LAKE";
            case ROCKY:
                return "ROCK";
            case JUNGLE:
                return "JUNGLE";
        }

        return null;
    }

}
