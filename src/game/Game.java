package game;

import game.player.*;
import game.player.exceptions.*;
import game.settlements.*;
import game.settlements.exceptions.*;
import game.world.*;
import game.tile.*;

public class Game {
    public World world;
    public Player opponent;
    public Player theAI;
    public Ai ai;

    public Game() {
        this.world = new World();
        this.opponent = new Player(this.world);
        this.theAI = new Player(this.world);
        this.ai = new Ai(this.world);
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void foundSettlement(Player currentlyActivePlayer, Hex settlementHex) throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        currentlyActivePlayer.foundSettlement(settlementHex);
    }

    public void expandSettlement(Player currentlyActivePlayer, Settlement existingSettlement, Terrain terrainBeingExpandedOnto) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NotEnoughPiecesException,
            NoHexesToExpandToException
    {
        currentlyActivePlayer.expandSettlement(existingSettlement, terrainBeingExpandedOnto);
    }

    public void buildTotoroSanctuary(Player currentlyActivePlayer, Hex sanctuaryHex) throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        currentlyActivePlayer.buildTotoroSanctuary(sanctuaryHex);
    }

    public void buildTigerPlayground(Player currentlyActivePlayer, Hex playgroundHex) throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        currentlyActivePlayer.buildTigerPlayground(playgroundHex);
    }
}
