package pieces;

import tile.Location;

public class Tiger extends Piece {

    public Tiger(Player owner, Location location, boolean played)  {
        super(owner, location, played);
    }

    public Player getTigerOwner() {
        return getOwner();
    }

    public Location getTigerLocation() {
        return getLocation();
    }

    public boolean getTigerStatus() {
        return getPlayStatus();
    }

    public void setTigerLocation(Location location) {
        setLocation(location);
    }

    public void setTigerStatus(boolean played) {
        setPlayStatus(played);
    }
}
