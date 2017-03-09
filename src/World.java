import tile.Hex;
import tile.Tile;

import java.util.ArrayList;

public class World {
    private ArrayList<ArrayList<Hex>> hexes;

    public World() {
        hexes = new ArrayList<>();
    }

    public Hex getHexByCoordinate(int x, int y) {
        return hexes.get(x).get(y);
    }

    public Tile getTileByCoordinate(int x, int y) {
        Hex hex = getHexByCoordinate(x,y);
        return hex.getOwner();
    }

    public void insertIntoWorldWithVolcanoAtCoordinate(Tile tile, int x, int y) {

    }
}
