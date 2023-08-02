package com.firstapp.android51;

public class Board {

    Tile[][] board;

    public Board(Tile[][] board){
        this.board = board;
    }

    public Tile[][] getBoard(){
        return board;
    }
}
