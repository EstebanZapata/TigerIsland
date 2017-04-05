package pieces;

import game.Player;
import tile.Location;

public class Villager extends Piece {

    public Villager(Player owner, Location location, boolean played) {
        super(owner, location, played);
    }

    public Player getVillagerOwner() {
        return getOwner();
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
