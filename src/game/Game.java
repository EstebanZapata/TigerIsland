package game;

import adapters.GuiAdapter;
import adapters.OutputAdapter;
import gui.Gui;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private World world;
    private List<OutputAdapter> outputAdapters;

    public Game() {
        outputAdapters = new ArrayList<>();
        this.world = new World();
        outputAdapters.add(new GuiAdapter());
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientation orientation) {
        try {
            world.insertTileIntoWorld(tile, locationOfVolcano, orientation);
            sendPlaceTileActionToAdapters(tile, locationOfVolcano, orientation);
        }
        catch (HexAlreadyAtLocationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendPlaceTileActionToAdapters(Tile tile, Location locationOfVolcano, TileOrientation orientation) {
        for(OutputAdapter adapter:outputAdapters) {
            adapter.placeTile(tile, locationOfVolcano, orientation);
        }
    }

}
