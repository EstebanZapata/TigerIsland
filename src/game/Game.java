package game;


import game.world.*;
import game.world.exceptions.*;
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
        try {
            world.placeFirstTile(tile, TileOrientationRelativeToVolcano.NORTHWEST_WEST);
            world.placeFirstTile(tile, TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);


        }
        catch (TilePlacementException e) {
            System.out.println(e.getMessage());
        }




    }


}
