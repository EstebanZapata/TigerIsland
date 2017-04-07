package game;

import tile.Location;

public class Start {
    public Game myGame;
    public void main(String[] args) {
        this.myGame = new Game();
        this.myGame.play();


    }


    static public String convertClientCoordinatesToServerCoordinates(Location locationToConvert) {
        int serverXCoordinate = locationToConvert.getXCoordinate();
        int serverZCoordinate = locationToConvert.getYCoordinate();
        int serverYCoordinate = -(serverXCoordinate + serverZCoordinate);
        String serverCoordinates = Integer.toString(serverXCoordinate) + ' ' + Integer.toString(serverYCoordinate) + ' ' + Integer.toString(serverZCoordinate);
        return serverCoordinates;
    }

}

//TODO: WORK ON THIS ONCE SERVER COMMUNICATION IS TAKEN CARE OF
//private Location convertServerCoordinatesToClientLocation(String locationFromServer) {
//        String[] splitIntoCoordinates = locationFromServer.split("\\s+");
//        int clientLocationXCoordinate = Integer.parseInt(splitIntoCoordinates[0]);
//        int clientLocationYCoordinate = Integer.parseInt(splitIntoCoordinates[2]);
//
//        return TileManager.getLocationDirectlyAboveHexByXY()
//        //return new Location(clientLocationXCoordinate, clientLocationYCoordinate, 0);
//
//}
