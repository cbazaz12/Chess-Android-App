package com.firstapp.android51;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Chess extends AppCompatActivity {

    public static Calendar calendar;
    public static ArrayList<Board> listOfBoards = new ArrayList<>();
    public static String result;
    public boolean isSelected;
    public static ImageButton selected;
    public static ImageButton undoSelectedPrev;
    public static ImageButton undoSelectedMove;
    public static Drawable prev;
    public static Drawable move;
    public static Tile[][] undoBoard;
    public static Tile[][] saveBoard;
    int selectedX;
    int selectedY;
    TextView selectedText;
    TextView turnText;
    int turn;
    boolean gameover;
    boolean hasUndone;

    public Drawable prevDraw;

    public static boolean aiHappening = false;

    public static String s;
    public static Tile[][] board;
    public static boolean allTrue;
    public static boolean check;
    public static boolean diag;
    public static boolean [][] mateCheckerB = {{false, false, false}, {false, false, false}, {false, false, false}};
    public static boolean [][] mateCheckerW = {{false, false, false}, {false, false, false}, {false, false, false}};

    public static boolean checkPromotion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfBoards.clear();
        setBoard();
        selectedText = findViewById(R.id.selectedText);
        turnText = findViewById(R.id.turnText);
        turn = 1;
        gameover = false;
        hasUndone = false;
        undoBoard = new Tile[8][8];
        result = "";
    }

    public void undo(View view) {
        if(hasUndone || turn == 1){
            return;
        }
        undoSelectedPrev.setImageDrawable(prev);
        undoSelectedMove.setImageDrawable(move);
        board = undoBoard;
        hasUndone = true;
        turn--;
        if (turn % 2 == 0) {
            turnText.setText("Black's Move");
        } else {
            turnText.setText("White's Move");
        }
        selectedText.setText("");
        listOfBoards.remove(listOfBoards.size()-1);
    }

    public void copyBoard(){
        undoBoard = new Tile[8][8];
        int alternate = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                String buttonId = "imageButton" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Tile insert = new Tile(alternate%2, findViewById(resId));
                undoBoard[i][j] = insert;
                alternate++;
            }
            alternate++;
        }
        for(int i = 0 ; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getPiece()!=null) {
                    Piece p = board[i][j].getPiece();
                    if (p.getName().equals("wp") || p.getName().equals("bp")) {
                        Piece add = new Pawn(undoBoard, p.getColor(), j, i, p.getDrawable());
                        undoBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wB") || p.getName().equals("bB")) {
                        Piece add = new Bishop(undoBoard, p.getColor(), j, i, p.getDrawable());
                        undoBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wK") || p.getName().equals("bK")) {
                        Piece add = new King(undoBoard, p.getColor(), j, i, p.getDrawable());
                        undoBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wN") || p.getName().equals("bN")) {
                        Piece add = new Knight(undoBoard, p.getColor(), j, i, p.getDrawable());
                        undoBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wQ") || p.getName().equals("bQ")) {
                        Piece add = new Queen(undoBoard, p.getColor(), j, i, p.getDrawable());
                        undoBoard[i][j].setPiece(add);
                    } else{
                        Piece add = new Rook(undoBoard, p.getColor(), j, i, p.getDrawable());
                        undoBoard[i][j].setPiece(add);
                    }
                }
            }
        }
    }

    public void storeBoard(){
        saveBoard = new Tile[8][8];
        int alternate = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                String buttonId = "imageButton" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Tile insert = new Tile(alternate%2, findViewById(resId));
                saveBoard[i][j] = insert;
                alternate++;
            }
            alternate++;
        }
        for(int i = 0 ; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getPiece()!=null) {
                    Piece p = board[i][j].getPiece();
                    if (p.getName().equals("wp") || p.getName().equals("bp")) {
                        Piece add = new Pawn(saveBoard, p.getColor(), j, i, p.getDrawable());
                        saveBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wB") || p.getName().equals("bB")) {
                        Piece add = new Bishop(saveBoard, p.getColor(), j, i, p.getDrawable());
                        saveBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wK") || p.getName().equals("bK")) {
                        Piece add = new King(saveBoard, p.getColor(), j, i, p.getDrawable());
                        saveBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wN") || p.getName().equals("bN")) {
                        Piece add = new Knight(saveBoard, p.getColor(), j, i, p.getDrawable());
                        saveBoard[i][j].setPiece(add);
                    } else if (p.getName().equals("wQ") || p.getName().equals("bQ")) {
                        Piece add = new Queen(saveBoard, p.getColor(), j, i, p.getDrawable());
                        saveBoard[i][j].setPiece(add);
                    } else{
                        Piece add = new Rook(saveBoard, p.getColor(), j, i, p.getDrawable());
                        saveBoard[i][j].setPiece(add);
                    }
                }
            }
        }
        Board b = new Board(saveBoard);
        listOfBoards.add(b);
    }

    public void movePiece(View view) {
        if(gameover){
            return;
        }
        int i = -1;
        int j = -1;
        if (selected != null && selected.getId() == view.getId()) {
            //selected self twice
            selected.setImageDrawable(prevDraw);
            isSelected = false;
            selected = null;
            selectedText.setText("");
            return;
        }
        if (view.getId() == R.id.imageButton00) {
            i = 0;
            j = 0;
        } else if (view.getId() == R.id.imageButton01) {
            i = 0;
            j = 1;
        } else if (view.getId() == R.id.imageButton02) {
            i = 0;
            j = 2;
        } else if (view.getId() == R.id.imageButton03) {
            i = 0;
            j = 3;
        } else if (view.getId() == R.id.imageButton04) {
            i = 0;
            j = 4;
        } else if (view.getId() == R.id.imageButton05) {
            i = 0;
            j = 5;
        } else if (view.getId() == R.id.imageButton06) {
            i = 0;
            j = 6;
        } else if (view.getId() == R.id.imageButton07) {
            i = 0;
            j = 7;
        } else if (view.getId() == R.id.imageButton10) {
            i = 1;
            j = 0;
        } else if (view.getId() == R.id.imageButton11) {
            i = 1;
            j = 1;
        } else if (view.getId() == R.id.imageButton12) {
            i = 1;
            j = 2;
        } else if (view.getId() == R.id.imageButton13) {
            i = 1;
            j = 3;
        } else if (view.getId() == R.id.imageButton14) {
            i = 1;
            j = 4;
        } else if (view.getId() == R.id.imageButton15) {
            i = 1;
            j = 5;
        } else if (view.getId() == R.id.imageButton16) {
            i = 1;
            j = 6;
        } else if (view.getId() == R.id.imageButton17) {
            i = 1;
            j = 7;
        } else if (view.getId() == R.id.imageButton20) {
            i = 2;
            j = 0;
        } else if (view.getId() == R.id.imageButton21) {
            i = 2;
            j = 1;
        } else if (view.getId() == R.id.imageButton22) {
            i = 2;
            j = 2;
        } else if (view.getId() == R.id.imageButton23) {
            i = 2;
            j = 3;
        } else if (view.getId() == R.id.imageButton24) {
            i = 2;
            j = 4;
        } else if (view.getId() == R.id.imageButton25) {
            i = 2;
            j = 5;
        } else if (view.getId() == R.id.imageButton26) {
            i = 2;
            j = 6;
        } else if (view.getId() == R.id.imageButton27) {
            i = 2;
            j = 7;
        } else if (view.getId() == R.id.imageButton30) {
            i = 3;
            j = 0;
        } else if (view.getId() == R.id.imageButton31) {
            i = 3;
            j = 1;
        } else if (view.getId() == R.id.imageButton32) {
            i = 3;
            j = 2;
        } else if (view.getId() == R.id.imageButton33) {
            i = 3;
            j = 3;
        } else if (view.getId() == R.id.imageButton34) {
            i = 3;
            j = 4;
        } else if (view.getId() == R.id.imageButton35) {
            i = 3;
            j = 5;
        } else if (view.getId() == R.id.imageButton36) {
            i = 3;
            j = 6;
        } else if (view.getId() == R.id.imageButton37) {
            i = 3;
            j = 7;
        } else if (view.getId() == R.id.imageButton40) {
            i = 4;
            j = 0;
        } else if (view.getId() == R.id.imageButton41) {
            i = 4;
            j = 1;
        } else if (view.getId() == R.id.imageButton42) {
            i = 4;
            j = 2;
        } else if (view.getId() == R.id.imageButton43) {
            i = 4;
            j = 3;
        } else if (view.getId() == R.id.imageButton44) {
            i = 4;
            j = 4;
        } else if (view.getId() == R.id.imageButton45) {
            i = 4;
            j = 5;
        } else if (view.getId() == R.id.imageButton46) {
            i = 4;
            j = 6;
        } else if (view.getId() == R.id.imageButton47) {
            i = 4;
            j = 7;
        } else if (view.getId() == R.id.imageButton50) {
            i = 5;
            j = 0;
        } else if (view.getId() == R.id.imageButton51) {
            i = 5;
            j = 1;
        } else if (view.getId() == R.id.imageButton52) {
            i = 5;
            j = 2;
        } else if (view.getId() == R.id.imageButton53) {
            i = 5;
            j = 3;
        } else if (view.getId() == R.id.imageButton54) {
            i = 5;
            j = 4;
        } else if (view.getId() == R.id.imageButton55) {
            i = 5;
            j = 5;
        } else if (view.getId() == R.id.imageButton56) {
            i = 5;
            j = 6;
        } else if (view.getId() == R.id.imageButton57) {
            i = 5;
            j = 7;
        } else if (view.getId() == R.id.imageButton60) {
            i = 6;
            j = 0;
        } else if (view.getId() == R.id.imageButton61) {
            i = 6;
            j = 1;
        } else if (view.getId() == R.id.imageButton62) {
            i = 6;
            j = 2;
        } else if (view.getId() == R.id.imageButton63) {
            i = 6;
            j = 3;
        } else if (view.getId() == R.id.imageButton64) {
            i = 6;
            j = 4;
        } else if (view.getId() == R.id.imageButton65) {
            i = 6;
            j = 5;
        } else if (view.getId() == R.id.imageButton66) {
            i = 6;
            j = 6;
        } else if (view.getId() == R.id.imageButton67) {
            i = 6;
            j = 7;
        } else if (view.getId() == R.id.imageButton70) {
            i = 7;
            j = 0;
        } else if (view.getId() == R.id.imageButton71) {
            i = 7;
            j = 1;
        } else if (view.getId() == R.id.imageButton72) {
            i = 7;
            j = 2;
        } else if (view.getId() == R.id.imageButton73) {
            i = 7;
            j = 3;
        } else if (view.getId() == R.id.imageButton74) {
            i = 7;
            j = 4;
        } else if (view.getId() == R.id.imageButton75) {
            i = 7;
            j = 5;
        } else if (view.getId() == R.id.imageButton76) {
            i = 7;
            j = 6;
        } else if (view.getId() == R.id.imageButton77) {
            i = 7;
            j = 7;
        }
        if (!isSelected) {
            if (((ImageButton) view).getDrawable() == null) {
//                selected.setImageDrawable(prevDraw);
                return;
            }
            isSelected = true;
            selected = (ImageButton) view;
            prevDraw = selected.getDrawable();
//            selected.setImageResource(R.drawable.gpawn);
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (board[x][y] != null) {
                        if (board[x][y].getImageButton().equals(selected)) {
                            if (board[x][y].getPiece().getColor() != (turn % 2)) {
                                isSelected = false;
                                selected = null;
                                return;
                            }
                            prev = selected.getDrawable();
                            String content = "Selected piece: " + board[i][j].getPiece().getName();
                            if (board[i][j].getPiece().getName().equals("bB") || board[i][j].getPiece().getName().equals("wB")){
                                selected.setImageResource(R.drawable.gbishop);
                            }
                            else if (board[i][j].getPiece().getName().equals("bK") || board[i][j].getPiece().getName().equals("wK")){
                                selected.setImageResource(R.drawable.gking);
                            }
                            else if (board[i][j].getPiece().getName().equals("bN") || board[i][j].getPiece().getName().equals("wN")){
                                selected.setImageResource(R.drawable.gknight);
                            }
                            else if (board[i][j].getPiece().getName().equals("bp") || board[i][j].getPiece().getName().equals("wp")){
                                selected.setImageResource(R.drawable.gpawn);
                            }
                            else if (board[i][j].getPiece().getName().equals("bQ") || board[i][j].getPiece().getName().equals("wQ")){
                                selected.setImageResource(R.drawable.gqueen);
                            }
                            else if (board[i][j].getPiece().getName().equals("bR") || board[i][j].getPiece().getName().equals("wR")){
                                selected.setImageResource(R.drawable.grook);
                            }
                            selectedText.setText(content);
                            selectedX = i;
                            selectedY = j;
                        }
                    }
                }
            }
        } else {
            copyBoard();
            if (board[selectedX][selectedY].getPiece().move(j, i)) {
                if(checkPromotion){
                    Promotion.i = i;
                    Promotion.j = j;
                    Intent intent = new Intent(this, Promotion.class);
                    startActivity(intent);
                    checkPromotion = false;
                }
                board[i][j].getPiece().lastTime = Piece.timer;
                Piece.timer++;
                allTrue = false;
                if(turn%2 == 1){
                    fillMateCheckerB();
                    boolean checkMate = true;
                    check = false;
                    if(mateCheckerB[1][1]){
                        check = true;
                    }
                    for(int x = 0; x < 3; x++){
                        for(int y = 0; y < 3; y ++){
                            if(!mateCheckerB[x][y]){
                                checkMate = false;
                            }
                        }
                    }
                    if(checkMate){
                        allTrue = true;
                        if(!canPieceBlockMateB()){
                            String content = "Checkmate";
                            selectedText.setText(content);
                            content = "White wins";
                            turnText.setText(content);
                            gameover = true;
                            storeBoard();
                            result = "White wins";
                            Intent intent = new Intent(Chess.this, SaveGameScreen.class);
                            startActivity(intent);
                            return;
                        }
                        String print = "Check";
                        selectedText.setText(print);
                    }
                    else if(check){
                        String print = "Check";
                        selectedText.setText(print);
                    }
                }
                else{
                    fillMateCheckerW();
                    boolean checkMate = true;
                    check = false;
                    if(mateCheckerW[1][1]){
                        check = true;
                    }
                    for(int x = 0; x < 3; x++){
                        for(int y = 0; y < 3; y ++){
                            if(!mateCheckerW[x][y]){
                                checkMate = false;
                            }
                        }
                    }
                    if(checkMate){
                        allTrue = true;
                        // System.out.println(canPieceBlockMateW());
                        if(!canPieceBlockMateW()){
                            String content = "Checkmate";
                            selectedText.setText(content);
                            content = "Black wins";
                            turnText.setText(content);
                            gameover = true;
                            storeBoard();
                            result = "Black wins";
                            Intent intent = new Intent(Chess.this, SaveGameScreen.class);
                            startActivity(intent);
                            return;
                        }
                        String print = "Check";
                        selectedText.setText(print);
                    }
                    else if(check){
                        String print = "Check";
                        selectedText.setText(print);
                    }
                }

                ImageButton add = (ImageButton) view;
                undoSelectedPrev = selected;
                undoSelectedMove = add;
                move = add.getDrawable();
//                add.setImageDrawable(selected.getDrawable()); //PREVDRAW
                add.setImageDrawable(prevDraw);
                selected.setImageResource(0);
                isSelected = false;
                selected = null;
                turn++;
                hasUndone = false;
                storeBoard();
                if (turn % 2 == 0) {
                    turnText.setText("Black's Move");
                } else {
                    turnText.setText("White's Move");
                }
                if(check){
                    return;
                }
                selectedText.setText("");
            }
        }
    }
    public static void fillMateCheckerB(){
        // fill all of mateCheckers back to false
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                mateCheckerB[i][j] = false;
            }
        }

        // find black king position on board
        int kingX = -5;
        int kingY = -5;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if((board[j][i].getPiece().getName()).equals("bK")){
                        kingX = i;
                        kingY = j;
                    }
                }
            }
        }
        Piece temp = board[kingY][kingX].getPiece();
        board[kingY][kingX].setPiece(null);
        // fill mateCheckerB[0][0]
        int tempX;
        int tempY;
        if(kingX-1 >= 0 && kingY-1 >= 0){
            tempX = kingX-1;
            tempY = kingY-1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[0][0] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[0][0] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[0][0] = true;
                }
            }
        }
        else{
            mateCheckerB[0][0] = true;
        }
        // fill mateCheckerB[0][1]
        if(kingY-1 >= 0){
            tempX = kingX;
            tempY = kingY-1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[0][1] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[0][1] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[0][1] = true;
                }
            }
        }
        else{
            mateCheckerB[0][1] = true;
        }
        // fill mateCheckerB[0][2]
        if(kingX+1 <= 7 && kingY-1 >= 0){
            tempX = kingX+1;
            tempY = kingY-1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[0][2] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[0][2] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[0][2] = true;
                }
            }
        }
        else{
            mateCheckerB[0][2] = true;
        }
        // fill mateCheckerB[1][0]
        if(kingX-1 >= 0){
            tempX = kingX-1;
            tempY = kingY;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[1][0] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[1][0] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[1][0] = true;
                }
            }
        }
        else{
            mateCheckerB[1][0] = true;
        }
        // fill mateCheckerB[1][1]
        tempX = kingX;
        tempY = kingY;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if(board[j][i].getPiece().getColor() == 1){
                        Piece save = board[tempY][tempX].getPiece();
                        board[tempY][tempX].setPiece(null);
                        if(board[j][i].getPiece()!=null){
                            if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                    diag = false;
                                    mateCheckerB[1][1] = true;
                                }
                            }
                            else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                mateCheckerB[1][1] = true;
                                // System.out.println(board[j][i].getPiece().getName());
                            }
                        }
                        board[tempY][tempX].setPiece(save);
                    }
                }
            }
        }
        // fill mateCheckerB[1][2]
        if(kingX+1 <= 7){
            tempX = kingX+1;
            tempY = kingY;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[1][2] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[1][2] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[1][2] = true;
                }
            }
        }
        else{
            mateCheckerB[1][2] = true;
        }
        // fill mateCheckerB[2][0]
        if(kingX-1 >= 0 && kingY+1 <= 7){
            tempX = kingX-1;
            tempY = kingY+1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[2][0] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[2][0] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[2][0] = true;
                }
            }
        }
        else{
            mateCheckerB[2][0] = true;
        }
        // fill mateCheckerB[2][1]
        if(kingY+1 <= 7){
            tempX = kingX;
            tempY = kingY+1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[2][1] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[2][1] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[2][1] = true;
                }
            }
        }
        else{
            mateCheckerB[2][1] = true;
        }
        // fill mateCheckerB[2][2]
        if(kingX+1 <= 7 && kingY+1 <= 7){
            tempX = kingX+1;
            tempY = kingY+1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 1){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerB[2][2] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerB[2][2] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 0){
                    mateCheckerB[2][2] = true;
                }
            }
        }
        else{
            mateCheckerB[2][2] = true;
        }
        board[kingY][kingX].setPiece(temp);
    }
    public static void fillMateCheckerW(){
        // fill all of mateCheckers back to false
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                mateCheckerW[i][j] = false;
            }
        }

        // find white king position on board
        int kingX = -5;
        int kingY = -5;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if((board[j][i].getPiece().getName()).equals("wK")){
                        kingX = i;
                        kingY = j;
                    }
                }
            }
        }
        Piece temp = board[kingY][kingX].getPiece();
        board[kingY][kingX].setPiece(null);
        // fill mateCheckerW[0][0]
        int tempX;
        int tempY;
        if(kingX-1 >= 0 && kingY-1 >= 0){
            tempX = kingX-1;
            tempY = kingY-1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[0][0] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[0][0] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[0][0] = true;
                }
            }
        }
        else{
            mateCheckerW[0][0] = true;
        }
        // fill mateCheckerB[0][1]
        if(kingY-1 >= 0){
            tempX = kingX;
            tempY = kingY-1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[0][1] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[0][1] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[0][1] = true;
                }
            }
        }
        else{
            mateCheckerW[0][1] = true;
        }
        // fill mateCheckerB[0][2]
        if(kingX+1 <= 7 && kingY-1 >= 0){
            tempX = kingX+1;
            tempY = kingY-1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[0][2] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[0][2] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[0][2] = true;
                }
            }
        }
        else{
            mateCheckerW[0][2] = true;
        }
        // fill mateCheckerB[1][0]
        if(kingX-1 >= 0){
            tempX = kingX-1;
            tempY = kingY;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[1][0] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[1][0] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[1][0] = true;
                }
            }
        }
        else{
            mateCheckerW[1][0] = true;
        }
        // fill mateCheckerB[1][1]
        tempX = kingX;
        tempY = kingY;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if(board[j][i].getPiece().getColor() == 0){
                        Piece save = board[tempY][tempX].getPiece();
                        board[tempY][tempX].setPiece(null);
                        if(board[j][i].getPiece()!=null){
                            if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                    diag = false;
                                    mateCheckerW[1][1] = true;
                                }
                            }
                            else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                mateCheckerW[1][1] = true;
                            }
                        }
                        board[tempY][tempX].setPiece(save);
                    }
                }
            }
        }
        // fill mateCheckerB[1][2]
        if(kingX+1 <= 7){
            tempX = kingX+1;
            tempY = kingY;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[1][2] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[1][2] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[1][2] = true;
                }
            }
        }
        else{
            mateCheckerW[1][2] = true;
        }
        // fill mateCheckerB[2][0]
        if(kingX-1 >= 0 && kingY+1 <= 7){
            tempX = kingX-1;
            tempY = kingY+1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[2][0] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[2][0] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[2][0] = true;
                }
            }
        }
        else{
            mateCheckerW[2][0] = true;
        }
        // fill mateCheckerB[2][1]
        if(kingY+1 <= 7){
            tempX = kingX;
            tempY = kingY+1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[2][1] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[2][1] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[2][1] = true;
                }
            }
        }
        else{
            mateCheckerW[2][1] = true;
        }
        // fill mateCheckerB[2][2]
        if(kingX+1 <= 7 && kingY+1 <= 7){
            tempX = kingX+1;
            tempY = kingY+1;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[j][i].getPiece() != null){
                        if(board[j][i].getPiece().getColor() == 0){
                            Piece save = board[tempY][tempX].getPiece();
                            board[tempY][tempX].setPiece(null);
                            if(board[j][i].getPiece()!=null){
                                if(board[j][i].getPiece().getName().equals("wp") || board[j][i].getPiece().getName().equals("bp")){
                                    if(board[j][i].getPiece().canDiag(tempX, tempY)){
                                        diag = false;
                                        mateCheckerW[2][2] = true;
                                    }
                                }
                                else if(board[j][i].getPiece().isValid(tempX, tempY)){
                                    mateCheckerW[2][2] = true;
                                }
                            }
                            board[tempY][tempX].setPiece(save);
                        }
                    }
                }
            }
            if(board[tempY][tempX].getPiece()!=null){
                if(board[tempY][tempX].getPiece().getColor() == 1){
                    mateCheckerW[2][2] = true;
                }
            }
        }
        else{
            mateCheckerW[2][2] = true;
        }
        board[kingY][kingX].setPiece(temp);
    }
    public static boolean canPieceBlockMateW(){
        // find white king position on board
        int kingX = -5;
        int kingY = -5;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if((board[j][i].getPiece().getName()).equals("wK")){
                        kingX = i;
                        kingY = j;
                    }
                }
            }
        }
        //loops thru all white pieces on board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if(board[j][i].getPiece().getColor() == 1 && !board[j][i].getPiece().getName().equals("wK")){
                        // loops thru all possible moves
                        for(int x = 0; x < 8; x++){
                            for(int y = 0; y < 8; y++){
                                if(board[j][i].getPiece().isValid(x, y)
                                        || (board[j][i].getPiece().getName().equals("wp") && board[j][i].getPiece().canDiag(x, y))){
                                    // find if piece can block check on king
                                    Piece initial = board[j][i].getPiece();
                                    board[j][i].setPiece(null);
                                    Piece save = board[y][x].getPiece();
                                    board[y][x].setPiece(initial);
                                    boolean kingDead = false;
                                    for(int h = 0; h < 8; h++){
                                        for(int e = 0; e < 8; e++){
                                            if(board[e][h].getPiece() != null){
                                                if(board[e][h].getPiece().getColor() == 0){
                                                    if(board[e][h].getPiece().isValid(kingX, kingY)
                                                            || (board[e][h].getPiece().getName().equals("wp") && board[e][h].getPiece().canDiag(kingX, kingY))){
                                                        kingDead = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    board[j][i].setPiece(initial);
                                    board[y][x].setPiece(save);
                                    if(kingDead == false){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean canPieceBlockMateB(){
        // find black king position on board
        int kingX = -5;
        int kingY = -5;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if((board[j][i].getPiece().getName()).equals("bK")){
                        kingX = i;
                        kingY = j;
                    }
                }
            }
        }
        //loops thru all black pieces on board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i].getPiece() != null){
                    if(board[j][i].getPiece().getColor() == 0 && !board[j][i].getPiece().getName().equals("bK")){
                        // loops thru all possible moves
                        for(int x = 0; x < 8; x++){
                            for(int y = 0; y < 8; y++){
                                if(board[j][i].getPiece().isValid(x, y)
                                        || (board[j][i].getPiece().getName().equals("bp") && board[j][i].getPiece().canDiag(x, y))){
                                    // find if piece can block check on king
                                    Piece initial = board[j][i].getPiece();
                                    board[j][i].setPiece(null);
                                    Piece save = board[y][x].getPiece();
                                    board[y][x].setPiece(initial);
                                    boolean kingDead = false;
                                    for(int h = 0; h < 8; h++){
                                        for(int e = 0; e < 8; e++){
                                            if(board[e][h].getPiece() != null){
                                                if(board[e][h].getPiece().getColor() == 1){
                                                    if(board[e][h].getPiece().isValid(kingX, kingY)
                                                            || (board[e][h].getPiece().getName().equals("wp") && board[e][h].getPiece().canDiag(kingX, kingY))){
                                                        kingDead = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    board[j][i].setPiece(initial);
                                    board[y][x].setPiece(save);
                                    if(kingDead == false){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setBoard(){
        board = new Tile[8][8];
        int alternate = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                String buttonId = "imageButton" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Tile insert = new Tile(alternate%2, findViewById(resId));
                board[i][j] = insert;
                alternate++;
            }
            alternate++;
        }
        Piece bK = new King(board, 0, 4, 0, R.drawable.bking);
        board[0][4].setPiece(bK);
        Piece wK = new King(board, 1, 4, 7, R.drawable.wking);
        board[7][4].setPiece(wK);
        Piece bB1 = new Bishop(board, 0, 2, 0, R.drawable.bbishop);
        board[0][2].setPiece(bB1);
        Piece bB2 = new Bishop(board, 0, 5, 0, R.drawable.bbishop);
        board[0][5].setPiece(bB2);
        Piece wB1 = new Bishop(board, 1, 2, 7, R.drawable.wbishop);
        board[7][2].setPiece(wB1);
        Piece wB2 = new Bishop(board, 1, 5, 7, R.drawable.wbishop);
        board[7][5].setPiece(wB2);
        Piece bR1 = new Rook(board, 0, 0, 0, R.drawable.brook);
        board[0][0].setPiece(bR1);
        Piece bR2 = new Rook(board, 0, 7, 0, R.drawable.brook);
        board[0][7].setPiece(bR2);
        Piece wR1 = new Rook(board, 1, 0, 7, R.drawable.wrook);
        board[7][0].setPiece(wR1);
        Piece wR2 = new Rook(board, 1, 7, 7, R.drawable.wrook);
        board[7][7].setPiece(wR2);
        Piece bQ = new Queen(board, 0, 3, 0, R.drawable.bqueen);
        board[0][3].setPiece(bQ);
        Piece wQ = new Queen(board, 1, 3, 7, R.drawable.wqueen);
        board[7][3].setPiece(wQ);
        Piece bN = new Knight(board, 0, 1, 0, R.drawable.bknight);
        board[0][1].setPiece(bN);
        Piece bN1 = new Knight(board, 0, 6, 0, R.drawable.bknight);
        board[0][6].setPiece(bN1);
        Piece wN = new Knight(board, 1, 1, 7, R.drawable.wknight);
        board[7][1].setPiece(wN);
        Piece wN1 = new Knight(board, 1, 6, 7, R.drawable.wknight);
        board[7][6].setPiece(wN1);
        for(int i = 0; i < 8; i++){
            Piece pawn = new Pawn(board, 0, i, 1, R.drawable.bpawn);
            board[1][i].setPiece(pawn);
        }
        for(int i = 0; i < 8; i++){
            Piece pawn = new Pawn(board, 1, i, 6, R.drawable.wpawn);
            board[6][i].setPiece(pawn);
        }
        storeBoard();
    }

    public void exit(View view) {
        Intent intent = new Intent(Chess.this, HomeScreen.class);
        startActivity(intent);
    }

    public void draw(View view) {
        result = "Draw";
        Intent intent = new Intent(Chess.this, SaveGameScreen.class);
        startActivity(intent);
    }

    public void resign(View view) {
        if(turn%2 == 0){
            result = "White Wins";
        }
        else{
            result = "Black Wins";
        }
        Intent intent = new Intent(Chess.this, SaveGameScreen.class);
        startActivity(intent);
    }

    public void aiClick(View view) {
        if (bothKings() == false){
            selectedText.setText("Game Over");
            return;
        }
        copyBoard();
        aiHappening = true;
        //reset
        if (selected != null && prevDraw!= null){
            selected.setImageDrawable(prevDraw);
        }
        isSelected = false;
        selected = null;
        selectedText.setText("");
        if(turn % 2 == 1){
            if(canWMove()){
                allTrue = false;
                if(turn%2 == 1){
                    fillMateCheckerB();
                    boolean checkMate = true;
                    check = false;
                    if(mateCheckerB[1][1]){
                        check = true;
                    }
                    for(int x = 0; x < 3; x++){
                        for(int y = 0; y < 3; y ++){
                            if(!mateCheckerB[x][y]){
                                checkMate = false;
                            }
                        }
                    }
                    if(checkMate){
                        allTrue = true;
                        if(!canPieceBlockMateB()){
                            String content = "Checkmate";
                            selectedText.setText(content);
                            content = "White wins";
                            turnText.setText(content);
                            gameover = true;
                            storeBoard();
                            result = "White wins";
                            Intent intent = new Intent(Chess.this, SaveGameScreen.class);
                            startActivity(intent);
                            return;
                        }
                        String print = "Check";
                        selectedText.setText(print);
                    }
                    else if(check){
                        String print = "Check";
                        selectedText.setText(print);
                    }
                }
                else{
                    fillMateCheckerW();
                    boolean checkMate = true;
                    check = false;
                    if(mateCheckerW[1][1]){
                        check = true;
                    }
                    for(int x = 0; x < 3; x++){
                        for(int y = 0; y < 3; y ++){
                            if(!mateCheckerW[x][y]){
                                checkMate = false;
                            }
                        }
                    }
                    if(checkMate){
                        allTrue = true;
                        // System.out.println(canPieceBlockMateW());
                        if(!canPieceBlockMateW()){
                            String content = "Checkmate";
                            selectedText.setText(content);
                            content = "Black wins";
                            turnText.setText(content);
                            gameover = true;
                            storeBoard();
                            result = "Black wins";
                            Intent intent = new Intent(Chess.this, SaveGameScreen.class);
                            startActivity(intent);
                            return;
                        }
                        String print = "Check";
                        selectedText.setText(print);
                    }
                    else if(check){
                        String print = "Check";
                        selectedText.setText(print);
                    }
                }
                isSelected = false;
                selected = null;
                hasUndone = false;
                storeBoard();
                turnText.setText("Black's Move");
                turn++;
            }
        }
        else{
            if(canBMove()){
                allTrue = false;
                if(turn%2 == 1){
                    fillMateCheckerB();
                    boolean checkMate = true;
                    check = false;
                    if(mateCheckerB[1][1]){
                        check = true;
                    }
                    for(int x = 0; x < 3; x++){
                        for(int y = 0; y < 3; y ++){
                            if(!mateCheckerB[x][y]){
                                checkMate = false;
                            }
                        }
                    }
                    if(checkMate){
                        allTrue = true;
                        if(!canPieceBlockMateB()){
                            String content = "Checkmate";
                            selectedText.setText(content);
                            content = "White wins";
                            turnText.setText(content);
                            gameover = true;
                            result = "White wins";
                            storeBoard();
                            Intent intent = new Intent(Chess.this, SaveGameScreen.class);
                            startActivity(intent);
                            return;
                        }
                        String print = "Check";
                        selectedText.setText(print);
                    }
                    else if(check){
                        String print = "Check";
                        selectedText.setText(print);
                    }
                }
                else{
                    fillMateCheckerW();
                    boolean checkMate = true;
                    check = false;
                    if(mateCheckerW[1][1]){
                        check = true;
                    }
                    for(int x = 0; x < 3; x++){
                        for(int y = 0; y < 3; y ++){
                            if(!mateCheckerW[x][y]){
                                checkMate = false;
                            }
                        }
                    }
                    if(checkMate){
                        allTrue = true;
                        // System.out.println(canPieceBlockMateW());
                        if(!canPieceBlockMateW()){
                            String content = "Checkmate";
                            selectedText.setText(content);
                            content = "Black wins";
                            turnText.setText(content);
                            gameover = true;
                            result = "Black wins";
                            storeBoard();
                            Intent intent = new Intent(Chess.this, SaveGameScreen.class);
                            startActivity(intent);
                            return;
                        }
                        String print = "Check";
                        selectedText.setText(print);
                    }
                    else if(check){
                        String print = "Check";
                        selectedText.setText(print);
                    }
                }
                isSelected = false;
                selected = null;
                hasUndone = false;
                storeBoard();
                turnText.setText("White's Move");
                turn++;
            }
        }
        aiHappening = false;
    }
    public boolean canWMove(){
        //loops thru all white pieces on board
        boolean [][]checkMoved = new boolean[8][8];
        Random random = new Random();
        int i = random.nextInt(8);
        int j = random.nextInt(8);
        while(movesLeft(i, j, checkMoved)) {
            if (board[i][j].getPiece() != null) {
                if (board[i][j].getPiece().getColor() == 1) {
                    // loops thru all possible moves
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (board[i][j].getPiece().move(y, x)) {
                                ImageButton selectedButton = board[i][j].getImageButton();
                                undoSelectedPrev = board[i][j].getImageButton();
                                prev = board[i][j].getImageButton().getDrawable();
                                ImageButton movedButton = board[x][y].getImageButton();
                                undoSelectedMove = board[x][y].getImageButton();
                                move = board[x][y].getImageButton().getDrawable();
                                movedButton.setImageDrawable(selectedButton.getDrawable());
                                selectedButton.setImageResource(0);
                                board[x][y].getPiece().lastTime = Piece.timer;
                                Piece.timer++;
                                return true;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
            i = random.nextInt(8);
            j = random.nextInt(8);
        }
        return false;
    }
    public boolean canBMove(){
        //loops thru all white pieces on
        boolean [][]checkMoved = new boolean[8][8];
        Random random = new Random();
        int i = random.nextInt(8);
        int j = random.nextInt(8);
        while(movesLeft(i, j, checkMoved)) {
            if (board[i][j].getPiece() != null) {
                if (board[i][j].getPiece().getColor() == 0) {
                    // loops thru all possible moves
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (board[i][j].getPiece().move(y, x)) {
                                ImageButton selectedButton = board[i][j].getImageButton();
                                undoSelectedPrev = board[i][j].getImageButton();
                                prev = board[i][j].getImageButton().getDrawable();
                                ImageButton movedButton = board[x][y].getImageButton();
                                undoSelectedMove = board[x][y].getImageButton();
                                move = board[x][y].getImageButton().getDrawable();
                                movedButton.setImageDrawable(selectedButton.getDrawable());
                                selectedButton.setImageResource(0);

                                board[x][y].getPiece().lastTime = Piece.timer;
                                Piece.timer++;
                                return true;
                            } else {
                                continue;
                            }
                        }
                    }

                }
            }
            i = random.nextInt(8);
            j = random.nextInt(8);
        }
        return false;
    }
    public boolean movesLeft(int i, int j, boolean checkMoved[][]){
        checkMoved[i][j] = true;
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(checkMoved[x][y] == false){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean bothKings(){
        int n = 0;
        for(int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if(board[x][y].getPiece() != null){
                    if(board[x][y].getPiece().getName().equals("bK")
                            || board[x][y].getPiece().getName().equals("wK")){
                        n++;
                    }
                }
            }
        }
        if(n == 2){
            return true;
        }
        return false;
    }
}