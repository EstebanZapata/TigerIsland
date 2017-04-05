package pieces;

import game.Player;
import tile.Location;

public class Totoro extends Piece {

    public Totoro(Player owner, Location location, boolean played)  {
        super(owner, location, played);
    }

    public Player getTotoroOwner() {
        return getOwner();
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
