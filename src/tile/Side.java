package tile;

public class Side {
    private Side adjacentSide;
    private Hex owner;
    
    static Side NO_ADJACENT_SIDE = new Side(null);

    Side(Hex owner) {
        this.owner = owner;
        this.adjacentSide = NO_ADJACENT_SIDE;
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

    private void setAdjacentSide(Side adjacentSide) {
        this.adjacentSide = adjacentSide;
    }

    public Hex getAdjacentSideOwner() throws NoAdjacentSideException {
        if(this.adjacentSide != NO_ADJACENT_SIDE) {
            return this.adjacentSide.getOwner();
        }
        else {
            throw new NoAdjacentSideException("Attempted to get owner of adjacent side when there is no adjacent side.");
        }
    }

}
