package com.firstapp.android51;

import android.widget.ImageButton;

public class Tile {
    int color;
    //black = 0, white = 1;
    Piece pieceOnTile;
    ImageButton image;

    Tile(int c, ImageButton image){
        color = c;
        pieceOnTile = null;
        this.image = image;
    }
    Tile(Piece p){
        pieceOnTile = p;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int c){
        color = c;
    }

    public Piece getPiece(){
        return pieceOnTile;
    }

    public void setPiece(Piece p){
        pieceOnTile = p;
    }

    public ImageButton getImageButton(){
        return image;
    }
}
