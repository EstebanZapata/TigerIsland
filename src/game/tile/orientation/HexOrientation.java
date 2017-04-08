package game.tile.orientation;

public enum HexOrientation {
    SOUTHWEST(0),
    WEST(1),
    NORTHWEST(2),
    NORTHEAST(3),
    EAST(4),
    SOUTHEAST(5);

    private int orientationInteger;

    HexOrientation(int orientationInteger) {
        this.orientationInteger = orientationInteger;
    }

    public int getOrientationInteger() {
        return this.orientationInteger;
    }


}
