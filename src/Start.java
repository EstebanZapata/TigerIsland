import game.Game;
import tile.Location;
import tile.Tile;
import tile.orientation.TileOrientation;

public class Start {
    public static void main(String[] args) {
        Game game = new Game();

        Tile tile = game.drawTile();
        game.insertTileIntoWorld(tile, new Location(0,0,0), TileOrientation.SOUTHWEST_SOUTHEAST);
    }


}
