package com.firstapp.android51;

public class Knight extends Piece{
    String name;
    int posX = 0;
    int posY = 0;

    public Knight(Tile[][] board, int color, int posX, int posY, int drawable){
        this.board = board;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.drawable = drawable;
    }

    public String getName(){
        if(color == 0) {
            return "bN";
        }
        else{
            return "wN";
        }
    }

    public boolean move(int x, int y){
        if(foresight(x, y) == false){
            return false;
        }
        if(Chess.allTrue == true){
            if(!isValidCheck(x, y)){
                return false;
            }
        }
        if(Chess.check == true){
            if(!isValidCheck(x, y)){
                return false;
            }
        }
        if(isValid(x,y)){
            if(!(posX == x && posY == y)){
                board[y][x].setPiece(board[posY][posX].getPiece());
                board[posY][posX].setPiece(null);
                posX = x;
                posY = y;
                return true;
            }
            else {  //shouldn't happen, can't move to same spot
                System.out.println("same spot");
                return false;
            }
        }
        System.out.println("try again");
        return false;
    }

    public boolean isValid(int x, int y){
        int opposite = 0;
        if (this.color == 0){
            opposite = 1;
        }
        if(posX + 1 == x && posY + 2 == y && (board[y][x].getPiece() == null        //1
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX - 1 == x && posY - 2 == y && (board[y][x].getPiece() == null   //2
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX + 2 == x && posY + 1 == y && (board[y][x].getPiece() == null   //3
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX - 2 == x && posY - 1 == y && (board[y][x].getPiece() == null   //4
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX + 2 == x && posY - 1 == y && (board[y][x].getPiece() == null   //5
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX - 2 == x && posY + 1 == y && (board[y][x].getPiece() == null   //6
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX + 1 == x && posY - 2 == y && (board[y][x].getPiece() == null    //7
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        else if(posX - 1 == x && posY + 2 == y && (board[y][x].getPiece() == null
                || board[y][x].getPiece().getColor() == opposite)){
            return true;
        }
        return false;
    }

    public boolean isValidCheck(int x, int y){
        int kingX = -5;
        int kingY = -5;
        if(this.color == 1){
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
            if(isValid(x, y)){
                Piece initial = board[posY][posX].getPiece();
                board[posY][posX].setPiece(null);
                Piece save =  board[y][x].getPiece();
                board[y][x].setPiece(initial);
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(board[j][i].getPiece() != null){
                            if(board[j][i].getPiece().getColor() == 0){
                                if(board[j][i].getPiece().isValid(kingX, kingY)
                                        || (board[j][i].getPiece().getName().equals("bp") && board[j][i].getPiece().canDiag(kingX, kingY))){
                                    board[posY][posX].setPiece(initial);
                                    board[y][x].setPiece(save);
                                    return false;
                                }
                            }
                        }
                    }
                }
                board[posY][posX].setPiece(initial);
                board[y][x].setPiece(save);
            }
        }
        else{
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
            if(isValid(x, y)){
                Piece initial = board[posY][posX].getPiece();
                board[posY][posX].setPiece(null);
                Piece save =  board[y][x].getPiece();
                board[y][x].setPiece(initial);
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(board[j][i].getPiece() != null){
                            if(board[j][i].getPiece().getColor() == 1){
                                if(board[j][i].getPiece().isValid(kingX, kingY)
                                        || (board[j][i].getPiece().getName().equals("wp") && board[j][i].getPiece().canDiag(kingX, kingY))){
                                    board[posY][posX].setPiece(initial);
                                    board[y][x].setPiece(save);
                                    return false;
                                }
                            }
                        }
                    }
                }
                board[posY][posX].setPiece(initial);
                board[y][x].setPiece(save);
            }
        }
        return true;
    }

    public boolean foresight(int x, int y){
        if(isValid(x, y) == true){
            Piece save = board[y][x].getPiece();
            board[y][x].setPiece(board[posY][posX].getPiece());
            board[posY][posX].setPiece(null);
            // System.out.println("1");
            if (this.color == 0){
                Chess.fillMateCheckerB();
                //reset
                board[posY][posX].setPiece(board[y][x].getPiece());
                board[y][x].setPiece(save);
                if (Chess.mateCheckerB[1][1] == true){
                    //reset
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                Chess.fillMateCheckerW();
                //reset
                board[posY][posX].setPiece(board[y][x].getPiece());
                board[y][x].setPiece(save);
                if (Chess.mateCheckerW[1][1] == true){
                    // System.out.println("f2");
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        else {
            // System.out.println("f3");
            return false;
        }
    }
}
