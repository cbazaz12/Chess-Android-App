package com.firstapp.android51;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Game implements Serializable {
    public ArrayList<DrawableArray> boardsList;
    public String name;
    public String gameResult;
    public long date;

    public Game(ArrayList<DrawableArray> boardsList, String name, String gameResult, long date){
        this.boardsList = boardsList;
        this.name = name;
        this.gameResult = gameResult;
        this.date = date;
    }



    public ArrayList<DrawableArray> getBoards(){
        return boardsList;
    }
    public String getName(){
        return name;
    }
    public String getGameResult(){
        return gameResult;
    }
    public long getDate(){ return date; }

    public String toString() { return name; }
}
