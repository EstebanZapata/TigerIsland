package pieces;

import tile.Location;

public class villager {
    private int villagerID;
    private String color;
    private Location location;
    private String status;  // in_play or not_yet_played or killed

    public villager(int villagerID, String color, Location location, String status) {
        this.villagerID = villagerID;
        this.color = color;
        this.location = location;
        this.status = status;
    }

    public int getVillagerIDID() {
        return villagerID;
    }

    public void setVillagerID(int villagerID) {
        this.villagerID = villagerID;
    }

    public String getVillagerColor() {
        return color;
    }

    public void setVillagerColor(String color) {
        this.color = color;
    }

    public Location getVillagerLocation() {
        return location;
    }

    public void setVillagerLocation(Location location) {
        this.location = location;
    }

    public String getVillagerStatus() {
        return status;
    }

    public void setVillagerStatus(String status) {
        this.status = status;
    }
}
