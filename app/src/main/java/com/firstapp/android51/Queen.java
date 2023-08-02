package com.firstapp.android51;

public class Queen extends Piece{
    int posX = 0;
    int posY = 0;

    public Queen(Tile[][] board, int color, int posX, int posY, int drawable){
        this.board = board;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.drawable = drawable;
    }

    public String getName(){
        if(color == 0) {
            return "bQ";
        }
        else{
            return "wQ";
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
                // System.out.println("same spot");
                return false;
            }
        }
        // System.out.println("tryyy again");
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

    public boolean isValid(int x, int y){

        //bishop movement
        int xm = posX+1;
        int ym = posY+1;
        while(ym <= 7 && xm <= 7){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            ym++;
            xm++;
        }
        xm = posX-1;
        ym = posY+1;
        while(ym <= 7 && xm >= 0){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            ym++;
            xm--;
        }
        xm = posX-1;
        ym = posY-1;
        while(ym >= 0 && xm >= 0){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            ym--;
            xm--;
        }
        xm = posX+1;
        ym = posY-1;
        while(ym >= 0 && xm <= 7){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            ym--;
            xm++;
        }


        //rook movement
        xm = posX+1;
        ym = posY;
        while(xm <= 7){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            xm++;
        }
        xm = posX;
        ym = posY+1;
        while(ym <= 7){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            ym++;
        }
        xm = posX;
        ym = posY-1;
        while(ym >= 0){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            ym--;
        }
        xm = posX-1;
        ym = posY;
        while(xm >= 0){
            if(board[ym][xm].getPiece()!=null){
                if(board[ym][xm].getPiece().getColor() == color){
                    break;
                }
            }
            if(ym == y && xm == x){
                return true;
            }
            if(board[ym][xm].getPiece()!=null){
                break;
            }
            xm--;
        }

        return false;
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
