package pieces;

import tile.Location;

public class Piece {

    private Player owner;
    private Location location;
    private boolean played;  // played = true, not_played = false

    public Piece(Player owner, Location location, boolean played)
    {
        this.owner = owner;
        this.location = location;
        this.played = played;
    }

    public Player getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public boolean getPlayStatus() {
        return played;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPlayStatus(boolean played)
    {
        this.played = played;
    }
}
