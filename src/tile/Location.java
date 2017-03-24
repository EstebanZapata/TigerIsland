package tile;

public class Location {
    private int xCoordinate;
    private int yCoordinate;
    private int zCoordinate;

    public Location(int xCoordinate, int col, int height) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = col;
        this.zCoordinate = height;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getzCoordinate() {
        return zCoordinate;
    }
}
