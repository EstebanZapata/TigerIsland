package pieces;

import pieces.*;
import game.HexAlreadyAtLocationException;
import game.Player;
import tile.Location;

import java.util.Vector;

public class PieceContainer {
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;
    private Vector<Totoro> totoros = new Vector<Totoro>(STARTING_TOTORO_COUNT);
    private Vector<Tiger> tigers = new Vector<Tiger>(STARTING_TIGER_COUNT);
    private Vector<Villager> villagers = new Vector<Villager>(STARTING_VILLAGER_COUNT);

    public PieceContainer(Player owner){
        int i;

        for (i = 0; i<STARTING_TOTORO_COUNT; i++){
            totoros.add(i,new Totoro(owner, new Location(0,0,0),false));
        }
        for (i = 0; i<STARTING_TIGER_COUNT; i++){
            tigers.add(i,new Tiger(owner, new Location(0,0,0),false));
        }
        for (i = 0; i<STARTING_VILLAGER_COUNT; i++){
            villagers.add(i,new Villager(owner, new Location(0,0,0),false));

        }
    };

    public void playAVillager() throws NotEnoughPiecesException {
        if (getRemainingVillagerCount() == 0) {
            String errorMessage = String.format("You do not have enough Villagers");
            throw new NotEnoughPiecesException(errorMessage);
        }

        villagers.remove(0);
    }

    public void playATotoro() throws NotEnoughPiecesException {
        if (getRemainingTotoroCount() == 0) {
            String errorMessage = String.format("You do not have enough Totoros");
            throw new NotEnoughPiecesException(errorMessage);
        }

        totoros.remove(0);
    }

    public void playATiger() throws NotEnoughPiecesException {
        if (getRemainingTigerCount() == 0) {
            String errorMessage = String.format("You do not have enough Tigers");
            throw new NotEnoughPiecesException(errorMessage);
        }

        tigers.remove(0);
    }

    public int getRemainingTotoroCount(){
        return totoros.size();
    }

    public int getRemainingTigerCount(){
        return tigers.size();
    }

    public int getRemainingVillagerCount(){
        return villagers.size();
    }

}
