package game.world;

import tile.Location;
import tile.Tile;
import tile.orientation.HexOrientation;

import java.util.ArrayList;
import java.util.Iterator;

public class CoordinateSystem {

    public static Location getLocationRelativeToOrientationAndCenter(Location center, HexOrientation hexOrientation) {
        Location[] adjacentHexLocations = getHexLocationsAdjacentToCenter(center);

        return adjacentHexLocations[hexOrientation.getOrientationInteger()];

    }

    public static Location[] getAdjacentHexLocationsToTile(Location[] hexLocations) {
        ArrayList<Location> adjacentLocations = new ArrayList<>();

        Location[] locationsAdjacentToVolcano = getHexLocationsAdjacentToCenter(hexLocations[0]);
        Location[] locationsAdjacentToLeftHex = getHexLocationsAdjacentToCenter(hexLocations[1]);
        Location[] locationsAdjacentToRightHex = getHexLocationsAdjacentToCenter(hexLocations[2]);

        for(Location locationAdjacentToVolcano:locationsAdjacentToVolcano) {
            adjacentLocations.add(locationAdjacentToVolcano);
        }


        for (Location locationAdjacentToLeftHex:locationsAdjacentToLeftHex) {
            for(int i = 0; i < adjacentLocations.size(); i++) {
                Location adjacentLocation = adjacentLocations.get(i);

                if (locationsAreNotEqual(locationAdjacentToLeftHex, adjacentLocation)) {
                    adjacentLocations.add(locationAdjacentToLeftHex);
                }
            }
        }

        for (Location locationAdjacentToRightHex:locationsAdjacentToRightHex) {
            for(int i = 0; i < adjacentLocations.size(); i++) {
                Location adjacentLocation = adjacentLocations.get(i);

                if (locationsAreNotEqual(locationAdjacentToRightHex, adjacentLocation)) {
                    adjacentLocations.add(locationAdjacentToRightHex);
                }
            }
        }

        return adjacentLocations.toArray(new Location[adjacentLocations.size()]);


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
}
