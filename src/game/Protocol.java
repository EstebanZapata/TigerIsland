package game;

import java.net.*;
import java.io.*;

public class Protocol {
    private int state = 0;
    String pid = "<pid>";
    String cid = "<cid>";
    String rounds = "<rounds>";
    String rid = "<rid>";

      private String[] startup = {"WELCOME TO ANOTHER EDITION OF THUNDERDOME!",
            "TWO SHALL ENTER, ONE SHALL LEAVE",
            "WAIT FOR THE TOURNAMENT TO BEGIN " + pid+"\n",
            "NEW CHALLENGE " +cid+ " YOU WILL PLAY" + rounds + "MATCHES\n",
            "BEGIN ROUND " + rid +" of " + rounds};
    public String processInput(String theInput) {
        String theOutput = null;
        if (state < startup.length) {
            theOutput = startup[state];
            state++;
        }
        else {
            theOutput = "TEST";
        }
        return theOutput;
    }
}
