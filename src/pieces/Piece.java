package pieces;

import tile.Location;

public class Piece {

    private Location location;
    private boolean played;  // played = true, not_played = false

    public Piece(Location location, boolean played)
    {
        this.location = location;
        this.played = played;
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
