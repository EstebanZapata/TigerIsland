package pieces;

import UnitTests.tile.Location;

public class Tiger extends Piece {

    public Tiger(Location location, boolean played)  {
        super(location, played);
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
