package game;

import game.player.Player;
import game.settlements.Settlement;
import game.settlements.exceptions.NoPlayableHexException;
import game.settlements.exceptions.NoSettlementOnHexException;
import game.settlements.exceptions.SettlementAlreadyHasTotoroSanctuaryException;
import game.settlements.exceptions.SettlementDoesNotSizeRequirementsException;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Tile;
import game.world.World;
import game.world.rules.exceptions.AiDangerException;
import game.world.rules.exceptions.IllegalTilePlacementException;
import thread.message.GameActionMessage;

import static game.settlements.BuildAction.FOUNDED_SETTLEMENT;
import static game.tile.Terrain.GRASSLANDS;
import static game.tile.orientation.TileOrientation.NORTHEAST_NORTHWEST;
import static game.tile.orientation.TileOrientation.NORTHWEST_WEST;

public class Ai {
    private int settleCount;
    public Player p1;
    public Player p2;
    public World world;
    public Location previousBuildHex = null;
    public Ai(World world, Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        this.world = world;
        this.settleCount = 0;
    }

    public GameActionMessage chooseMove(String gameID, int moveNumber, String playerID, Tile tileToBePlaced) throws IllegalTilePlacementException {
        try{
            Hex hexToBePlacedNextTo = world.getLeftMostHex();
            int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 2;
            int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate() - 1;
            Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

            int buildXCoordinate = newTileXCoordinate;
            int buildYCoordinate = newTileYCoordinate-1;
            Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);
            boolean p1isActivePlayer;
            boolean canBuildTotoro = false;


            if(canBuildTotoro) {
                //Build Totoro
                previousBuildHex = null;
                settleCount=0;
                canBuildTotoro = false;
            }
            else {
                if (previousBuildHex == null)
                    previousBuildHex = locationOnWhichToBuild;
                else if (settleCount < 3 && p1.settlementManager.getSettlementFromHex(world.getHexByCoordinate(previousBuildHex.getxCoordinate() - 2, previousBuildHex.getyCoordinate(), previousBuildHex.getHeight())) == null &&
                        p2.settlementManager.getSettlementFromHex(world.getHexByCoordinate(previousBuildHex.getxCoordinate() - 2, previousBuildHex.getyCoordinate(), previousBuildHex.getHeight())) == null) {
                    locationOnWhichToBuild = new Location(previousBuildHex.getxCoordinate() - 2, previousBuildHex.getyCoordinate(), 0);
                } else if (settleCount < 5 && settleCount >= 3 && p1.settlementManager.getSettlementFromHex(world.getHexByCoordinate(previousBuildHex.getxCoordinate() - 2, previousBuildHex.getyCoordinate(), previousBuildHex.getHeight())) == null &&
                        p2.settlementManager.getSettlementFromHex(world.getHexByCoordinate(previousBuildHex.getxCoordinate() - 2, previousBuildHex.getyCoordinate(), previousBuildHex.getHeight())) == null) {
                    locationOnWhichToBuild = new Location(previousBuildHex.getxCoordinate() + settleCount - 2, previousBuildHex.getyCoordinate() - settleCount + 2, 0);


                }else if(settleCount>=5){
                    settleCount = 0;
                    if (playerID == p1.playerID) {
                        Location templocationOnWhichToBuild = new Location(previousBuildHex.getxCoordinate() + settleCount - 1, previousBuildHex.getyCoordinate() - settleCount + 1, 0);
                        try {
                            p1.settlementManager.buildTotoroSanctuary(world.getHexByLocation(locationOnWhichToBuild));
                            locationOnWhichToBuild = templocationOnWhichToBuild;
                        }
                        catch (NoPlayableHexException error) {
                            if (playerID == p2.playerID) {
                                templocationOnWhichToBuild = new Location(previousBuildHex.getxCoordinate() + settleCount - 1, previousBuildHex.getyCoordinate() - settleCount + 1, 0);
                                try {
                                    p2.settlementManager.buildTotoroSanctuary(world.getHexByLocation(locationOnWhichToBuild));
                                    locationOnWhichToBuild = templocationOnWhichToBuild;
                                } catch (NoPlayableHexException error2) {

                                }
                            }
                        }
                    }
                }
                else {
                    String error = "AI DANGER";
                    throw new AiDangerException(error);
                }

                settleCount++;
            }
            System.out.println(locationOfNewTile.getxCoordinate());
            System.out.println(locationOfNewTile.getyCoordinate());
            System.out.println(NORTHEAST_NORTHWEST);
            world.insertTileIntoTileManager(tileToBePlaced,locationOfNewTile,NORTHEAST_NORTHWEST);
            return new GameActionMessage(gameID, moveNumber, playerID, tileToBePlaced, locationOfNewTile, NORTHEAST_NORTHWEST, FOUNDED_SETTLEMENT, locationOnWhichToBuild, GRASSLANDS);

        }
        catch (AiDangerException error){
            Hex hexToBePlacedNextTo = world.getLeftMostHex();
            int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 1;
            int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate();
            Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

            int buildXCoordinate = newTileXCoordinate - 1;
            int buildYCoordinate = newTileYCoordinate;
            Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);
            world.insertTileIntoTileManager(tileToBePlaced,locationOfNewTile,NORTHWEST_WEST);
            return new GameActionMessage(gameID, moveNumber, playerID, tileToBePlaced, locationOfNewTile, NORTHWEST_WEST, FOUNDED_SETTLEMENT, locationOnWhichToBuild, GRASSLANDS);

        } catch (NoSettlementOnHexException e) {
            Hex hexToBePlacedNextTo = world.getLeftMostHex();
            int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 1;
            int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate();
            Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

            int buildXCoordinate = newTileXCoordinate - 1;
            int buildYCoordinate = newTileYCoordinate;
            Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);
            world.insertTileIntoTileManager(tileToBePlaced,locationOfNewTile,NORTHWEST_WEST);
            return new GameActionMessage(gameID, moveNumber, playerID, tileToBePlaced, locationOfNewTile, NORTHWEST_WEST, FOUNDED_SETTLEMENT, locationOnWhichToBuild, GRASSLANDS);

        }

    }

}
