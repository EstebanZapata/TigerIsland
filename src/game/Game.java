package game;


import game.player.exceptions.NotEnoughPiecesException;
import game.settlements.Settlement;
import game.settlements.exceptions.*;
import game.world.*;
import game.player.*;
import game.world.rules.exceptions.IllegalTilePlacementException;
import game.world.rules.exceptions.NoHexAtLocationException;
import tile.*;

import java.util.ArrayList;

public class Game {
    private static final int START_SETTLEMENT_HEX_HEIGHT_REQUIREMENT = 1;

    public World world;
    public Player player1;
    public Player player2;

    public Game() {
        this.world = new World();
        this.player1 = new Player();
        this.player2 = new Player();
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
                try {
                    foundSettlement(player1);
                } catch (BuildConditionsNotMetException e) {
                    System.out.println(e.getMessage());
                }
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

    public void foundSettlement(Player currentlyActivePlayer) throws BuildConditionsNotMetException {
        try {
            currentlyActivePlayer.foundSettlement(world);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }
        catch (NoPlayableHexException e) {
            System.out.println(e.getMessage());
        }
    }

    public void expandSettlement(Player currentlyActivePlayer) throws
            NotEnoughPiecesException,
            NoHexesToExpandToException
    {
        try {
            currentlyActivePlayer.expandSettlement(world);
        }
        catch (NotEnoughPiecesException e) {
            throw new NotEnoughPiecesException(e.getMessage());
        }
        catch (NoHexesToExpandToException e) {
            throw new NoHexesToExpandToException(e.getMessage());
        }
    }

    public void buildTotoroSanctuary(Player currentlyActivePlayer)  {
        try {
            currentlyActivePlayer.buildTotoroSanctuary(world);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }
        catch (BuildConditionsNotMetException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildTigerPlayground(Player currentlyActivePlayer) {
        try {
            currentlyActivePlayer.buildTigerPlayground(world);
        }
        catch (NotEnoughPiecesException e) {
            System.out.println(e.getMessage());
        }
        catch (BuildConditionsNotMetException e) {
            System.out.println(e.getMessage());
        }
    }
}
