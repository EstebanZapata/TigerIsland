package game;


import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientationRelativeToVolcano;

public class Game {
    private World world;

    public Game() {
        this.world = new World();
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientationRelativeToVolcano orientation) {
        try {
            world.insertTileIntoWorld(tile, locationOfVolcano, orientation);
        }
        catch (HexAlreadyAtLocationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void play()  {
        Tile tile = drawTile();
        try {
            world.placeFirstTile(tile, TileOrientationRelativeToVolcano.NORTHWEST_WEST);
            world.placeFirstTile(tile, TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);


        }
        catch (HexAlreadyAtLocationException e) {
            System.out.println(e.getMessage());
        }

        world.printAllHexesAndTheirInformation();


    }

}
