package io;

import java.net.*;
import java.io.*;



public class Protocol {
    private int state = 0;
    String pid = "<pid>";
    String pid2 = "<opppid>";
    String cid = "<cid>";
    String rounds = "<rounds>";
    String rid = "<rid>";
    String gid = "<gid>";
    String timeMove = "5";
    String moveNum = "0";
    String tile = "LAKE+JUNGLE";
    private String[] startup = {"WELCOME TO ANOTHER EDITION OF THUNDERDOME!",
            "TWO SHALL ENTER, ONE SHALL LEAVE",
            "WAIT FOR THE TOURNAMENT TO BEGIN " + pid,
            "NEW CHALLENGE " +cid+ " YOU WILL PLAY " + rounds + " MATCHES",
            "BEGIN ROUND " + rid +" of " + rounds,
            "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER "+ pid2,
            "MAKE YOUR MOVE IN GAME "+ gid+ " WITHIN "+timeMove+" SECONDS: MOVE "+moveNum+" PLACE " + tile,
            "GAME A MOVE 1 PLAYER " + pid2 + " PLACE ROCK+LAKE AT 2 1 3 2 BUILD TIGER PLAYGROUND AT 2 -1 -1",
            "GAME " + gid + " MOVE " + moveNum + " PLAYER " + pid + " PLACE ROCK+LAKE AT 1 1 0 2 BUILD TOTORO SANCTUARY AT 2 -1 2",
            "THANK YOU FOR PLAYING! GOODBYE"
      };
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
