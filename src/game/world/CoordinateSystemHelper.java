package game.world;

import tile.Location;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

import java.util.ArrayList;

public class CoordinateSystemHelper {

    public static Location getHexLocationRelativeToOrientationAndCenter(Location center, HexOrientation hexOrientation) {
        Location[] adjacentHexLocations = getHexLocationsAdjacentToCenter(center);

        return adjacentHexLocations[hexOrientation.getOrientationInteger()];
    }

    public static Location[] getHexLocationsAdjacentToCenter(Location center) {
        Location[] adjacentLocations = new Location[6];

        int xCoordinateOfCenter = center.getxCoordinate();
        int yCoordinateOfCenter = center.getyCoordinate();
        int zCoordinateOfCenter = center.getzCoordinate();


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
        ArrayList<Location> adjacentLocations = new ArrayList<>();

        Location[] locationsAdjacentToVolcano = getHexLocationsAdjacentToCenter(hexLocations[0]);
        Location[] locationsAdjacentToLeftHex = getHexLocationsAdjacentToCenter(hexLocations[1]);
        Location[] locationsAdjacentToRightHex = getHexLocationsAdjacentToCenter(hexLocations[2]);

        for(Location locationAdjacentToVolcano:locationsAdjacentToVolcano) {
            adjacentLocations.add(locationAdjacentToVolcano);
        }

        insertNonRepeatingLocationsIntoAdjacentLocations(adjacentLocations, locationsAdjacentToLeftHex);
        insertNonRepeatingLocationsIntoAdjacentLocations(adjacentLocations, locationsAdjacentToRightHex);

        return adjacentLocations.toArray(new Location[adjacentLocations.size()]);


    }

    private static void insertNonRepeatingLocationsIntoAdjacentLocations(ArrayList<Location> adjacentLocations, Location[] locationsAdjacentToHex) {
        for (Location locationAdjacentToLeftHex:locationsAdjacentToHex) {
            for(int i = 0; i < adjacentLocations.size(); i++) {
                Location adjacentLocation = adjacentLocations.get(i);

                if (locationsAreNotEqual(locationAdjacentToLeftHex, adjacentLocation)) {
                    adjacentLocations.add(locationAdjacentToLeftHex);
                }
            }
        }
    }

    private static boolean locationsAreNotEqual(Location locationAdjacentToLeftHex, Location locationAdjacentToVolcano) {
        int x1 = locationAdjacentToLeftHex.getxCoordinate();
        int y1 = locationAdjacentToLeftHex.getyCoordinate();
        int z1 = locationAdjacentToLeftHex.getzCoordinate();

        int x2 = locationAdjacentToVolcano.getxCoordinate();
        int y2 = locationAdjacentToVolcano.getyCoordinate();
        int z2 = locationAdjacentToVolcano.getzCoordinate();

        return !(x1==x2 && y1==y2 && z1==z2);
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
}
