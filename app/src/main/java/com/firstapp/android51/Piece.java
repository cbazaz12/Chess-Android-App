package com.firstapp.android51;

public abstract class Piece {
    public static int timer = 1;
    public int lastTime = 0;
    public Tile[][] board;
    public abstract String getName();
    public int color;
    //black = 0, white = 1;
    public int drawable;

    public abstract boolean move(int x, int y);
    public abstract boolean isValid(int x, int y);

    public int getColor(){
        return color;
    }
    public void setPass(int x, int y){
    }
    public boolean canDiag(int x, int y){
        return false;
    }
    public int getDrawable(){
        return drawable;
    }
}
