package game;


import game.world.*;
import game.world.rules.exceptions.*;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

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
            world.placeFirstTile(tile, TileOrientation.NORTHWEST_WEST);
            world.placeFirstTile(tile, TileOrientation.NORTHEAST_NORTHWEST);


        }
        catch (IllegalTilePlacementException e) {
            System.out.println(e.getMessage());
        }




    }


}
