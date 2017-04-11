package game;

import game.player.*;
import game.player.exceptions.*;
import game.settlements.*;
import game.settlements.exceptions.*;
import game.world.*;
import game.world.rules.exceptions.*;
import game.tile.*;

public class Game {
    public World world;
    public Player player1;
    public Player player2;
    public Ai ai;

    public Game() {
        this.world = new World();
        this.player1 = new Player(this.world);
        this.player2 = new Player(this.world);
        this.ai = new Ai(this.world);
    }

    public Tile drawTile() {
        return new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    public void play() {
        boolean player1Turn = false;
        boolean tilePlaced = false;

        while (true) {
            Tile tile = drawTile(); // need to add first tile condition

            if (player1Turn) {
                while (!tilePlaced) {
                    try {
                        world.placeFirstTile(); //****** see above
                        tilePlaced = true;
                    } catch (IllegalTilePlacementException e) {
                        System.out.println(e.getMessage());
                    }
                }

                tilePlaced = false;

                // player 1 chooses a build action
                //if cannot do any, break from while loop and set flag

                // found settlement code

            }
            else {
                while (!tilePlaced) {
                    try {
                        world.placeFirstTile(); //******
                        tilePlaced = true;
                    } catch (IllegalTilePlacementException e) {
                        System.out.println(e.getMessage());
                    }
                }

                tilePlaced = false;

                // player 2 chooses a build action
                    //if cannot do any, break from while loop and set flag
            }

            // merge settlements

            // check end of game conditions

            player1Turn = !player1Turn;
        }

        // check failed build action flag --> determine winner from turn
    }

    public void foundSettlement(Player currentlyActivePlayer, Hex settlementHex) throws
            NotEnoughPiecesException,
            SettlementAlreadyExistsOnHexException
    {
        currentlyActivePlayer.foundSettlement(settlementHex);
    }

    public void expandSettlement(Player currentlyActivePlayer, Settlement existingSettlement) throws
            SettlementCannotBeBuiltOnVolcanoException,
            NotEnoughPiecesException,
            NoHexesToExpandToException
    {
        currentlyActivePlayer.expandSettlement(existingSettlement);
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
