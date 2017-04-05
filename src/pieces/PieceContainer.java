package pieces;

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
