package tile;

public class Side {
    private Side adjacentSide;
    private Hex owner;

    Side(Hex owner) {
        this.owner = owner;
    }

    Side(Hex owner, Side adjacentSide) {
        this.owner = owner;
        setSidesAdjacentToEachOther(adjacentSide);
    }

    public Hex getOwner() {
        return this.owner;
    }

    public Side getAdjacentSide() {
        return this.adjacentSide;
    }

    public void setSidesAdjacentToEachOther(Side adjacentSide) {
        this.adjacentSide = adjacentSide;

        adjacentSide.setAdjacentSide(this);
    }

    public void setAdjacentSide(Side adjacentSide) {
        this.adjacentSide = adjacentSide;
    }
}
