package game;

public class MessageParser {
    private String tournamentPassword = "<tournament_password>";
    private String username = "<username>";
    private String userpassword = "<password>";
    private String enterDome = "ENTER THUNDERDOME "+tournamentPassword;
    private String introduction = "I AM "+username+" "+userpassword;
    private String pid = "<pid>";
    private String cid = "<cid>";
    private String response = "No Response";
    private String rid;
    private String numOfRounds;
    private String opponent;
    private String activeGame;

    public String parseServerInput(String s){
        System.out.println(s);
        String parts[] = s.split(" ");
        if (s.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")){
            System.out.println(enterDome);
            return enterDome;
        }
        else if (s.equals("TWO SHALL ENTER, ONE SHALL LEAVE")){
            System.out.println(introduction);
            return introduction;
        }
        else if (parts[0].equals("WAIT") && parts[1].equals("FOR")){
            pid = parts[6];
            //call to subroutine to name player(pid)
            return "WAITING";
        }
        else if (parts[0].equals("NEW") && parts[1].equals("CHALLENGE")){
            cid = parts[2];
            //call to subroutine to name challenge(cid)
        }
        else if (parts[0].equals("BEGIN")){
            rid = parts[2];
            numOfRounds = parts[4];
        }
        else if (parts[0].equals("NEW") && parts[1].equals("MATCH")){
            opponent = parts[parts.length-1];
        }
        else if (parts[0].equals("MAKE")){
            activeGame = parts[5];
            //call to subroutine to make move in activeGame
        }
        else if (parts[0].equals("GAME") && parts[2].equals("OVER")){
            String gameToEnd = parts[1];
            //call subroutine to kill gameToEnd
        }


      return response;
    }
}
