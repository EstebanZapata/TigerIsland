package game.tile;

import java.util.Objects;

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

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int col) {
        this.yCoordinate = col;
    }

    public void setzCoordinate(int height) {
        this.zCoordinate = height;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", xCoordinate,yCoordinate,zCoordinate);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof  Location)) {
            return false;
        }

        Location otherLocation = (Location) other;

        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        int otherX = otherLocation.getxCoordinate();
        int otherY = otherLocation.getyCoordinate();
        int otherZ = otherLocation.getzCoordinate();

        if (this.xCoordinate == otherX && this.yCoordinate == otherY && this.zCoordinate == otherZ) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate,yCoordinate,zCoordinate);
    }

}
