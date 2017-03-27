package pieces;

import tile.Location;

public class tiger {
    private int tigerID;
    private String color;
    private Location location;
    private String status;  // in_play or not_yet_played or killed

    public tiger(int tigerID, String color, Location location, String status) {
        this.tigerID = tigerID;
        this.color = color;
        this.location = location;
        this.status = status;
    }

    public int getTigerID() {
        return tigerID;
    }

    public void setTigerID(int tigerID) {
        this.tigerID = tigerID;
    }

    public String getTigerColor() {
        return color;
    }

    public void setTigerColor(String color) {
        this.color = color;
    }

    public Location getTigerLocation() {
        return location;
    }

    public void setTigerLocation(Location location) {
        this.location = location;
    }

    public String getTigerStatus() {
        return status;
    }

    public void setTigerStatus(String status) {
        this.status = status;
    }
}
