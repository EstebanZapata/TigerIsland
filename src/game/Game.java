package game;


import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientationRelativeToVolcano;

public class Game {
    public World world;

    public Game() {
        this.world = new World();
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void play()  {
        Tile tile = drawTile();
        world.printAllHexesAndTheirInformation();


    }


}
