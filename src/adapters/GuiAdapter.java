package adapters;

import gui.Gui;
import javafx.application.Application;
import tile.Location;
import tile.Tile;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

public class GuiAdapter implements OutputAdapter{
    private static Gui gui;

    public GuiAdapter() {
        gui.launch();
    }

    public void placeTile(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) {
        Location locationOfLeftHex;
        Location locationOfRightHex;

        HexOrientation orientationOfLeftHex = null;
        HexOrientation orientationOfRightHex = null;

        switch(tileOrientation) {
            case SOUTHWEST_SOUTHEAST:
                orientationOfLeftHex = HexOrientation.SOUTHWEST;
                orientationOfRightHex = HexOrientation.SOUTHEAST;
                break;

            case WEST_SOUTHWEST:
                orientationOfLeftHex = HexOrientation.WEST;
                orientationOfRightHex = HexOrientation.SOUTHWEST;
                break;

            case NORTHWEST_WEST:
                orientationOfLeftHex = HexOrientation.NORTHWEST;
                orientationOfRightHex = HexOrientation.WEST;
                break;

            case NORTHEAST_NORTHWEST:
                orientationOfLeftHex = HexOrientation.NORTHEAST;
                orientationOfRightHex = HexOrientation.NORTHWEST;
                break;

            case EAST_NORTHEAST:
                orientationOfLeftHex = HexOrientation.EAST;
                orientationOfRightHex = HexOrientation.NORTHEAST;
                break;

            case SOUTHEAST_EAST:
                orientationOfLeftHex = HexOrientation.SOUTHEAST;
                orientationOfRightHex = HexOrientation.EAST;
                break;
        }

        locationOfLeftHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfLeftHex);
        locationOfRightHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfRightHex);

        gui.createHexAtLocation(tile.getVolcanoHex(), locationOfVolcano.getxCoordinate(), locationOfVolcano.getyCoordinate(), locationOfVolcano.getzCoordinate());
        gui.createHexAtLocation(tile.getLeftHexRelativeToVolcano(), locationOfLeftHex.getxCoordinate(), locationOfLeftHex.getyCoordinate(), locationOfLeftHex.getzCoordinate());
        gui.createHexAtLocation(tile.getRightHexRelativeToVolcano(), locationOfRightHex.getxCoordinate(), locationOfRightHex.getyCoordinate(), locationOfRightHex.getzCoordinate());
    }

    private Location getLocationRelativeToOrientationAndCenter(Location center, HexOrientation orientation) {
        int xCoordinateOfCenter = center.getxCoordinate();
        int yCoordinateOfCenter = center.getyCoordinate();

        int xCoordinateOfHex = 0;
        int yCoordinateOfHex = 0;
        int zCoordinateOfHex = center.getzCoordinate();

        switch(orientation) {
            case SOUTHWEST:
                xCoordinateOfHex = xCoordinateOfCenter - 1;
                yCoordinateOfHex = yCoordinateOfCenter - 1;
                break;
            case WEST:
                xCoordinateOfHex = xCoordinateOfCenter - 1;
                yCoordinateOfHex = yCoordinateOfCenter;
                break;
            case NORTHWEST:
                xCoordinateOfHex = xCoordinateOfCenter;
                yCoordinateOfHex = yCoordinateOfCenter + 1;
                break;
            case NORTHEAST:
                xCoordinateOfHex = xCoordinateOfCenter + 1;
                yCoordinateOfHex = yCoordinateOfCenter + 1;
                break;
            case EAST:
                xCoordinateOfHex = xCoordinateOfCenter + 1;
                yCoordinateOfHex = yCoordinateOfCenter;
                break;
            case SOUTHEAST:
                xCoordinateOfHex = xCoordinateOfCenter;
                yCoordinateOfHex = yCoordinateOfCenter - 1;
                break;
        }

        return new Location(xCoordinateOfHex, yCoordinateOfHex, zCoordinateOfHex);

    }
}
