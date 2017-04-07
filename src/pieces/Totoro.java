package pieces;

import UnitTests.tile.Location;

public class Totoro extends Piece {

    public Totoro(Location location, boolean played)  {
        super(location, played);
    }


    public Location getTotoroLocation() {
        return getLocation();
    }

    public boolean getTotoroStatus() {
        return getPlayStatus();
    }

    public void setTotoroLocation(Location location) {
        setLocation(location);
    }

    public void setTotoroStatus(boolean played) {
        setPlayStatus(played);
    }
}
