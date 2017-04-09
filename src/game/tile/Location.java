package game.tile;

import java.util.Objects;

public class Location {
    private int xCoordinate;
    private int yCoordinate;
    private int height;

    public Location(int xCoordinate, int yCoordinate, int height) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.height = height;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getHeight() {
        return height;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int col) {
        this.yCoordinate = col;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", xCoordinate,yCoordinate, height);
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
        int otherZ = otherLocation.getHeight();

        if (this.xCoordinate == otherX && this.yCoordinate == otherY && this.height == otherZ) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate,yCoordinate, height);
    }

}
