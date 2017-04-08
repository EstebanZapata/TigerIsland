package game.world;

import tile.Location;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CoordinateSystemHelper {

    public static Location getTentativeLeftHexLocation(Location locationOfVolcano, TileOrientation tileOrientation) {
        HexOrientation leftHexOrientation = getLeftHexOrientationFromTileOrientation(tileOrientation);

        return getHexLocationRelativeToOrientationAndCenter(locationOfVolcano, leftHexOrientation);
    }

    public static Location getTentativeRightHexLocation(Location locationOfVolcano, TileOrientation tileOrientation) {
        HexOrientation rightHexOrientation = CoordinateSystemHelper.getRightHexOrientationFromTileOrientation(tileOrientation);

        return getHexLocationRelativeToOrientationAndCenter(locationOfVolcano, rightHexOrientation);
    }

    public static HexOrientation getLeftHexOrientationFromTileOrientation(TileOrientation tileOrientation) {
        HexOrientation hexOrientation = null;

        switch(tileOrientation) {
            case SOUTHWEST_SOUTHEAST:
                hexOrientation = HexOrientation.SOUTHWEST;
                break;

            case WEST_SOUTHWEST:
                hexOrientation = HexOrientation.WEST;
                break;

            case NORTHWEST_WEST:
                hexOrientation = HexOrientation.NORTHWEST;
                break;

            case NORTHEAST_NORTHWEST:
                hexOrientation = HexOrientation.NORTHEAST;
                break;

            case EAST_NORTHEAST:
                hexOrientation = HexOrientation.EAST;
                break;

            case SOUTHEAST_EAST:
                hexOrientation = HexOrientation.SOUTHEAST;
                break;
        }

        return hexOrientation;
    }

    public static HexOrientation getRightHexOrientationFromTileOrientation(TileOrientation tileOrientation) {
        HexOrientation hexOrientation = null;

        switch(tileOrientation) {
            case SOUTHWEST_SOUTHEAST:
                hexOrientation = HexOrientation.SOUTHEAST;
                break;

            case WEST_SOUTHWEST:
                hexOrientation = HexOrientation.SOUTHWEST;
                break;

            case NORTHWEST_WEST:
                hexOrientation = HexOrientation.WEST;
                break;

            case NORTHEAST_NORTHWEST:
                hexOrientation = HexOrientation.NORTHWEST;
                break;

            case EAST_NORTHEAST:
                hexOrientation = HexOrientation.NORTHEAST;
                break;

            case SOUTHEAST_EAST:
                hexOrientation = HexOrientation.EAST;
                break;
        }

        return hexOrientation;
    }


    public static Location getHexLocationRelativeToOrientationAndCenter(Location center, HexOrientation hexOrientation) {
        Location[] adjacentHexLocations = getHexLocationsAdjacentToCenter(center);

        return adjacentHexLocations[hexOrientation.getOrientationInteger()];
    }

    public static Location[] getHexLocationsAdjacentToCenter(Location center) {
        Location[] adjacentLocations = new Location[6];

        int xCoordinateOfCenter = center.getxCoordinate();
        int yCoordinateOfCenter = center.getyCoordinate();
        int zCoordinateOfCenter = center.getHeight();


        adjacentLocations[HexOrientation.SOUTHWEST.getOrientationInteger()] =
                new Location(xCoordinateOfCenter - 1, yCoordinateOfCenter - 1, zCoordinateOfCenter);
        adjacentLocations[HexOrientation.WEST.getOrientationInteger()] =
                new Location(xCoordinateOfCenter - 1, yCoordinateOfCenter, zCoordinateOfCenter);
        adjacentLocations[HexOrientation.NORTHWEST.getOrientationInteger()] =
                new Location(xCoordinateOfCenter, yCoordinateOfCenter + 1, zCoordinateOfCenter);
        adjacentLocations[HexOrientation.NORTHEAST.getOrientationInteger()] =
                new Location(xCoordinateOfCenter + 1, yCoordinateOfCenter + 1, zCoordinateOfCenter);
        adjacentLocations[HexOrientation.EAST.getOrientationInteger()] =
                new Location(xCoordinateOfCenter + 1, yCoordinateOfCenter, zCoordinateOfCenter);
        adjacentLocations[HexOrientation.SOUTHEAST.getOrientationInteger()] =
                new Location(xCoordinateOfCenter, yCoordinateOfCenter - 1, zCoordinateOfCenter);

        return adjacentLocations;

    }

    public static Location[] getAdjacentHexLocationsToTile(Location[] hexLocations) {

        Location[] locationsAdjacentToVolcano = getHexLocationsAdjacentToCenter(hexLocations[0]);
        Location[] locationsAdjacentToLeftHex = getHexLocationsAdjacentToCenter(hexLocations[1]);
        Location[] locationsAdjacentToRightHex = getHexLocationsAdjacentToCenter(hexLocations[2]);

        Location[] adjacentLocationsToTile =
                combineListsAndRemoveDuplicates(locationsAdjacentToVolcano, locationsAdjacentToLeftHex, locationsAdjacentToRightHex);

        return adjacentLocationsToTile;

    }

    private static Location[] combineListsAndRemoveDuplicates(Location[] locationsOne, Location[] locationsTwo, Location[] locationsThree){
        Set<Location> setWithoutDupes = new HashSet<>(Arrays.asList(locationsOne));
        setWithoutDupes.addAll(Arrays.asList(locationsTwo));
        setWithoutDupes.addAll(Arrays.asList(locationsThree));

        return setWithoutDupes.toArray(new Location[0]);



    }





}
