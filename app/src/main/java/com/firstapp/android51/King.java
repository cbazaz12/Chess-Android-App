package com.firstapp.android51;

import android.graphics.drawable.Drawable;

public class King extends Piece{
    String name;
    int posX = 0;
    int posY = 0;

    public King(Tile[][] board, int color, int posX, int posY, int drawable){
        this.board = board;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.drawable = drawable;
    }

    public String getName(){
        if(color == 0) {
            return "bK";
        }
        else{
            return "wK";
        }
    }

    public boolean move(int x, int y){
        if(canCastle(x, y)){
            if(x == posX+2){
                int xm = posX+1;
                while(xm < 8){
                    if(board[posY][xm].getPiece()!=null){
                        board[y][x].setPiece(board[posY][posX].getPiece());
                        board[posY][posX].setPiece(null);
                        board[posY][xm].getImageButton().setImageResource(0);
                        posX = x;
                        posY = y;
                        if(this.color == 0){
                            board[posY][x - 1].setPiece(new Rook(board, this.color, x-1, posY, R.drawable.brook));
                            board[posY][x - 1].getImageButton().setImageResource(R.drawable.brook);
                        }
                        else {
                            board[posY][x - 1].setPiece(new Rook(board, this.color, x-1, posY, R.drawable.wrook));
                            board[posY][x - 1].getImageButton().setImageResource(R.drawable.wrook);
                        }
                        board[posY][xm].setPiece(null);
                        return true;
                    }
                    xm++;
                }
            }
            else{
                int xm = posX-1;
                while(xm >= 0){
                    if(board[posY][xm].getPiece()!=null){
                        board[y][x].setPiece(board[posY][posX].getPiece());
                        board[posY][posX].setPiece(null);
                        board[posY][xm].getImageButton().setImageResource(0);
                        posX = x;
                        posY = y;
                        if(this.color == 0){
                            board[posY][x + 1].setPiece(new Rook(board, this.color, x+1, posY, R.drawable.brook));
                            board[posY][x + 1].getImageButton().setImageResource(R.drawable.brook);
                        }
                        else{
                            board[posY][x + 1].setPiece(new Rook(board, this.color, x+1, posY, R.drawable.wrook));
                            board[posY][x + 1].getImageButton().setImageResource(R.drawable.wrook);

                        }
                        board[posY][xm].setPiece(null);
                        return true;
                    }
                    xm--;
                }
            }
        }


        if (Chess.allTrue == true){
            return false;
        }
        if (isValidCheck(x, y)){
            Chess.check = false;
            //continues
        }
        else{
            return false;
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
                // System.out.println("same spot");
                return false;
            }
        }
        // System.out.println("try again");
        return false;
    }

    public boolean canCastle(int x, int y){
        if(x == posX-2 || x == posX+2){
            if(posX != 0 && posX != 7){
                int xm = posX+1;
                while(xm < 8){
                    if(board[posY][xm].getPiece()!=null){
                        if((board[posY][xm].getPiece().getName().equals("wR")) && this.color == 1){
                            if(board[posY][xm].getPiece().lastTime == 0){
                                return true;
                            }
                        }
                        else if((board[posY][xm].getPiece().getName().equals("bR")) && this.color == 0){
                            if(board[posY][xm].getPiece().lastTime == 0){
                                return true;
                            }
                        }
                        break;
                    }
                    xm++;
                }
                xm = posX-1;
                while(xm >= 0){
                    if(board[posY][xm].getPiece()!=null){
                        if((board[posY][xm].getPiece().getName().equals("wR")) && this.color == 1){
                            if(board[posY][xm].getPiece().lastTime == 0){
                                return true;
                            }
                        }
                        else if((board[posY][xm].getPiece().getName().equals("bR")) && this.color == 0){
                            if(board[posY][xm].getPiece().lastTime == 0){
                                return true;
                            }
                        }
                        break;
                    }
                    xm--;
                }
            }
        }
        return false;
    }

    public boolean isValid(int x, int y){
        if(board[y][x].getPiece()!=null){
            if(board[y][x].getPiece().getColor() == color){
                return false;
            }
        }
        if((x + 1 == posX || x == posX || x - 1 == posX) &&
                (y + 1 == posY || y == posY || y - 1 == posY) && (!(posX == x && posY == y))){
            return true;
        }
        return false;
    }

    public boolean isValidCheck(int x, int y){
        if(this.color == 0){
            if (posX - 1 == x && posY - 1 == y && Chess.mateCheckerB[0][0] == false){    //top left
                return true;
            }
            else if (posX == x && posY - 1 == y && Chess.mateCheckerB[0][1] == false){   //top
                return true;
            }
            else if (posX + 1 == x && posY - 1 == y && Chess.mateCheckerB[0][2] == false){   //top right
                return true;
            }
            else if (posX  - 1 == x && posY == y && Chess.mateCheckerB[1][0] == false){
                return true;
            }
            else if (posX + 1 == x && posY == y && Chess.mateCheckerB[1][2] == false){
                return true;
            }
            if (posX - 1 == x && posY + 1 == y && Chess.mateCheckerB[2][0] == false){    //bot left
                return true;
            }
            else if (posX == x && posY + 1 == y && Chess.mateCheckerB[2][1] == false){   //mid bot
                return true;
            }
            else if (posX + 1 == x && posY + 1 == y && Chess.mateCheckerB[2][2] == false){   //bot right
                return true;
            }
        }
        else{
            if (posX - 1 == x && posY - 1 == y && Chess.mateCheckerW[0][0] == false){    //top left
                return true;
            }
            else if (posX == x && posY - 1 == y && Chess.mateCheckerW[0][1] == false){   //top
                return true;
            }
            else if (posX + 1 == x && posY - 1 == y && Chess.mateCheckerW[0][2] == false){   //top right
                return true;
            }
            else if (posX  - 1 == x && posY == y && Chess.mateCheckerW[1][0] == false){
                return true;
            }
            else if (posX + 1 == x && posY == y && Chess.mateCheckerW[1][2] == false){
                return true;
            }
            if (posX - 1 == x && posY + 1 == y && Chess.mateCheckerW[2][0] == false){    //bot left
                return true;
            }
            else if (posX == x && posY + 1 == y && Chess.mateCheckerW[2][1] == false){   //mid bot
                return true;
            }
            else if (posX + 1 == x && posY + 1 == y && Chess.mateCheckerW[2][2] == false){   //bot right
                return true;
            }
        }
        return false;
    }
}
