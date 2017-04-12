package game;

import game.player.Player;
import game.tile.Tile;
import game.world.World;
import thread.message.GameActionMessage;

/**
 * Created by Liam on 4/11/2017.
 */
public class AiVersion3 {
    private World world;
    private Player theAI, opponent;

    public AiVersion3(World world, Player opponent, Player theAI) {
        this.world = world;
        this.opponent = opponent;
        this.theAI = theAI;

    }

    public GameActionMessage chooseMove(String gameId, int moveNumber, String myPlayerId, Tile tileToPlace) {



        return null;
        // return new GameActionMessage(gameId, moveNumber, myPlayerId, tileToPlace, );

    }
}
