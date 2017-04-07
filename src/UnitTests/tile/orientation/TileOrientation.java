package UnitTests.tile.orientation;

public enum TileOrientation {
    SOUTHWEST_SOUTHEAST(4),
    WEST_SOUTHWEST(5),
    NORTHWEST_WEST(6),
    NORTHEAST_NORTHWEST(1),
    EAST_NORTHEAST(2),
    SOUTHEAST_EAST(3);

    private int serverProtocolOrientation;

    TileOrientation(int serverProtocolOrientation) {
        this.serverProtocolOrientation = serverProtocolOrientation;
    }

    public int getServerProtocolOrientation() {
        return this.serverProtocolOrientation;
    }
}
