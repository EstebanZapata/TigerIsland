package pieces;

import tile.Location;

public class Villager extends Piece {

    public Villager(Location location, boolean played) {
        super(location, played);
    }

    public Location getVillagerLocation() {
        return getLocation();
    }

    public boolean getVillagerStatus() {
        return getPlayStatus();
    }

    public void setVillagerLocation(Location location) {
        setLocation(location);
    }

    public void setVillagerStatus(boolean played) {
        setPlayStatus(played);
    }
}
