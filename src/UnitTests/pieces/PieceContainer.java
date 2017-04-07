package UnitTests.pieces;

import UnitTests.tile.Location;

import java.util.Vector;

public class PieceContainer {
    private static final int STARTING_TOTORO_COUNT = 3;
    private static final int STARTING_TIGER_COUNT = 2;
    private static final int STARTING_VILLAGER_COUNT = 20;
    private Vector<Totoro> totoros = new Vector<Totoro>(STARTING_TOTORO_COUNT);
    private Vector<Tiger> tigers = new Vector<Tiger>(STARTING_TIGER_COUNT);
    private Vector<Villager> villagers = new Vector<Villager>(STARTING_VILLAGER_COUNT);


    public PieceContainer(){
        int i;

        for (i = 0; i<STARTING_TOTORO_COUNT; i++){
            totoros.add(i,new Totoro(new Location(0,0, 0),false));
        }
        for (i = 0; i<STARTING_TIGER_COUNT; i++){
            tigers.add(i,new Tiger(new Location(0,0, 0),false));
        }
        for (i = 0; i<STARTING_VILLAGER_COUNT; i++){
            villagers.add(i,new Villager(new Location(0,0, 0),false));

        }
    };
    public void playATotoro(){
        if(!totoros.isEmpty()){
            totoros.remove(0);
        }
        else{
            //Throw Exception
        }
    }
    public void playATiger(){
        if(!tigers.isEmpty()){
            tigers.remove(0);
        }
        else{
            //Throw Exception
        }
    }
    public void playAVillager(){
        if(!villagers.isEmpty()){
            villagers.remove(0);
        }
        else{
            //Throw Exception
        }
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
