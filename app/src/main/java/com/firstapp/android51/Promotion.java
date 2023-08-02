package com.firstapp.android51;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Promotion extends AppCompatActivity {

    public static int i;
    public static int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
    }

    public void clickKnight(View view) {
        int color = Chess.board[i][j].getPiece().getColor();
        if(color == 0){
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.bknight);
            Chess.board[i][j].setPiece(new Knight(Chess.board, color, j, i, R.drawable.bknight));
        }
        else{
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.wknight);
            Chess.board[i][j].setPiece(new Knight(Chess.board, color, j, i, R.drawable.wknight));
        }
        finish();
    }

    public void clickQueen(View view) {
        int color = Chess.board[i][j].getPiece().getColor();
        if(color == 0){
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.bqueen);
            Chess.board[i][j].setPiece(new Queen(Chess.board, color, j, i, R.drawable.bqueen));
        }
        else{
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.wqueen);
            Chess.board[i][j].setPiece(new Queen(Chess.board, color, j, i, R.drawable.wqueen));
        }
        finish();
    }

    public void clickRook(View view) {
        int color = Chess.board[i][j].getPiece().getColor();
        if(color == 0){
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.brook);
            Chess.board[i][j].setPiece(new Rook(Chess.board, color, j, i, R.drawable.brook));
        }
        else{
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.wrook);
            Chess.board[i][j].setPiece(new Rook(Chess.board, color, j, i, R.drawable.wrook));
        }
        finish();
    }

    public void clickBishop(View view) {
        int color = Chess.board[i][j].getPiece().getColor();
        if(color == 0){
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.bbishop);
            Chess.board[i][j].setPiece(new Bishop(Chess.board, color, j, i, R.drawable.bbishop));
        }
        else{
            Chess.board[i][j].getImageButton().setImageResource(R.drawable.wbishop);
            Chess.board[i][j].setPiece(new Bishop(Chess.board, color, j, i, R.drawable.wbishop));
        }
        finish();
    }
}