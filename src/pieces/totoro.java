package pieces;

import tile.Location;

public class totoro {
    private int totoroID;
    private String color;
    private Location location;
    private String status;  // in_play or not_yet_played or killed

    public totoro(int totoroID, String color, Location location, String status) {
        this.totoroID = totoroID;
        this.color = color;
        this.location = location;
        this.status = status;
    }

    public int getTotoroID() {
        return totoroID;
    }

    public void setTotoroID(int totoroID) {
        this.totoroID = totoroID;
    }

    public String getTotoroColor() {
        return color;
    }

    public void setTotoroColor(String color) {
        this.color = color;
    }

    public Location getTotoroLocation() {
        return location;
    }

    public void setTotoroLocation(Location location) {
        this.location = location;
    }

    public String getTotoroStatus() {
        return status;
    }

    public void setTotoroStatus(String status) {
        this.status = status;
    }
}
