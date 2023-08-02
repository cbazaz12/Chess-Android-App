package com.firstapp.android51;

public class Pawn extends Piece{
    String name;
    int posX = 0;
    int posY = 0;
    public boolean canPassant = false;     //line 62
    public int passX;
    public int passY;

    public Pawn(Tile[][] board, int color, int posX, int posY, int drawable){
        this.board = board;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.drawable = drawable;
    }

    public void setPass(int x, int y){
        this.passX = x;
        this.passY = y;
        canPassant = true;
    }

    public int getLastTime(){
        return this.lastTime;
    }

    public String getName(){
        if(color == 0) {
            return "bp";
        }
        else{
            return "wp";
        }
    }

    public boolean move(int x, int y){
        if(foresight(x, y) == false){
            return false;
        }
        if (!(posX == x && posY == y) && canEnPassant(x, y)){
            // this.lastTime = Piece.timer;
            // Piece.timer++;
            doEnPassant(x, y);
            this.canPassant = false;
            return true;
        }
        if(Chess.allTrue == true){
            if(isValidCheck(x, y)==false){
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
                //en passant
                // this.lastTime = Piece.timer;
                // Piece.timer++;
                //promotion
                if(y == 0 || y == 7){
                    return promotion(x, y);
                }

                checkHoriz(x,y);    //for en passant
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

    public boolean canDiag(int x, int y){
        if (this.color == 1){
            if((posY - 1 == y && posX - 1 == x) &&
                    (board[y][x].getPiece() != null )){    //move up left
                return true;
            }
            else if((posY - 1 == y && posX + 1 == x) &&
                    (board[y][x].getPiece() != null )){    //move up right
                return true;
            }
        }
        else {
            if((posY + 1 == y && posX - 1 == x) &&
                    (board[y][x].getPiece() != null )){    //move up left
                return true;
            }
            else if((posY + 1 == y && posX + 1 == x) &&
                    (board[y][x].getPiece() != null )){    //move up right
                return true;
            }
        }
        return false;
    }

    public boolean isValid(int x, int y){
        if (!(posX == x && posY == y) && canEnPassant(x, y)){
            return true;
        }
        int opposite = 0;
        if (this.color == 0){
            opposite = 1;
        }
        if (this.color == 1){
            if (lastTime == 0){
                if(((posY - 1 == y && posX == x) && (board[y][x].getPiece() == null))
                        || ((posY - 2 == y && posX == x) && (board[y+1][x].getPiece() == null) && (board[y][x].getPiece() == null))){                                              //move forward & empty
                    return true;
                }
                else if((posY - 1 == y && posX - 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up left
                    return true;
                }
                else if((posY - 1 == y && posX + 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up right
                    return true;
                }
                else {
                    return false;
                }
            }
            else{
                if((posY - 1 == y && posX == x) && (board[y][x].getPiece() == null)){      //move forward & empty
                    return true;
                }
                else if((posY - 1 == y && posX - 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up left
                    return true;
                }
                else if((posY - 1 == y && posX + 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up right
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else{
            if (lastTime == 0){
                if(((posY + 1 == y && posX == x) && (board[y][x].getPiece() == null))
                        || ((posY + 2 == y && posX == x) && (board[y-1][x].getPiece() == null)&& (board[y][x].getPiece() == null))){                                              //move forward & empty
                    return true;
                }
                else if((posY + 1 == y && posX - 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up left
                    return true;
                }
                else if((posY + 1 == y && posX + 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up right
                    return true;
                }
                else {
                    return false;
                }
            }
            else{
                if((posY + 1 == y && posX == x) && (board[y][x].getPiece() == null)){      //move forward & empty
                    return true;
                }
                else if((posY + 1 == y && posX - 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up left
                    return true;
                }
                else if((posY + 1 == y && posX + 1 == x) &&
                        (board[y][x].getPiece() != null && board[y][x].getPiece().getColor() == opposite)){    //move up right
                    return true;
                }
                else {
                    return false;
                }
            }
        }

    }

    public boolean promotion(int x, int y){
        if(Chess.aiHappening == false){
            if(this.color == 0){
                Chess.selected.setImageResource(R.drawable.bpawn);
                board[y][x].setPiece(new Pawn(board, this.color, x, y, R.drawable.bpawn));
                board[posY][posX].setPiece(null);
            }
            else{
                Chess.selected.setImageResource(R.drawable.wpawn);
                board[y][x].setPiece(new Pawn(board, this.color, x, y, R.drawable.wpawn));
                board[posY][posX].setPiece(null);
            }
            Chess.checkPromotion = true;
        }
        else{
            if(this.color == 0){
                board[y][x].setPiece(new Queen(board, this.color, x, y, R.drawable.bqueen));
                board[posY][posX].setPiece(null);
                board[posY][posX].getImageButton().setImageResource(R.drawable.bqueen);
            }
            else{
                board[y][x].setPiece(new Queen(board, this.color, x, y, R.drawable.wqueen));
                board[posY][posX].setPiece(null);
                board[posY][posX].getImageButton().setImageResource(R.drawable.wqueen);
            }
        }

        return true;
    }

    public boolean doEnPassant(int x, int y){
        // System.out.println("sans " + canPassant);
        //can do it, timer, position
        if (this.canPassant && this.color == 0){
            if(board[passY][passX].getPiece().lastTime + 1 == Piece.timer){
                if(this.color == 0 && y - 1 == passY && x == passX){
                    if((posY + 1 == y && posX - 1 == x) || (posY + 1 == y && posX + 1 == x)){
                        // System.out.println("works");
                        //updated for android
                        board[passY][passX].getImageButton().setImageResource(0);
                        board[passY][passX].setPiece(null);
                        board[y][x].setPiece(board[posY][posX].getPiece());
                        board[posY][posX].setPiece(null);
                        posX = x;
                        posY = y;
                        return true;
                    }
                }
            }
        }
        else if(this.canPassant && this.color == 1){
            if(board[passY][passX].getPiece().lastTime + 1 == Piece.timer){
                if(this.color == 1 && y + 1 == passY && x == passX){
                    if((posY - 1 == y && posX - 1 == x) || (posY - 1 == y && posX + 1 == x)){
                        // System.out.println("works");
                        //updated for android
                        board[passY][passX].getImageButton().setImageResource(0);
                        board[passY][passX].setPiece(null);
                        board[y][x].setPiece(board[posY][posX].getPiece());
                        board[posY][posX].setPiece(null);
                        posX = x;
                        posY = y;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canEnPassant(int x, int y){
        if (this.canPassant && this.color == 0){
            if(board[passY][passX].getPiece()!=null){
                if(board[passY][passX].getPiece().lastTime + 1 == Piece.timer){
                    if(this.color == 0 && y - 1 == passY && x == passX){
                        if((posY + 1 == y && posX - 1 == x) || (posY + 1 == y && posX + 1 == x)){
                            return true;
                        }
                    }
                }
            }
        }
        else if(this.canPassant && this.color == 1){
            if(board[passY][passX].getPiece()!=null){
                if(board[passY][passX].getPiece().lastTime + 1 == Piece.timer){
                    if(this.color == 1 && y + 1 == passY && x == passX){
                        if((posY - 1 == y && posX - 1 == x) || (posY - 1 == y && posX + 1 == x)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void checkHoriz(int x, int y){
        if(this.lastTime!=0){
            return;
        }
        //ERRORS, CHECK LATER
        // System.out.println("y");
        if (x + 1 <= 7 && board[y][x + 1].getPiece()!=null && board[y][x+1].getPiece() instanceof Pawn
                && this.color != board[y][x+1].getPiece().getColor()){
            board[y][x+1].getPiece().setPass(x, y);
            // System.out.println("yup1");
        }
        if (x - 1 >= 0 && board[y][x - 1].getPiece()!=null && board[y][x-1].getPiece() instanceof Pawn
                && this.color != board[y][x-1].getPiece().getColor()){
            board[y][x-1].getPiece().setPass(x, y);
            // System.out.println("yup2");
        }
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
            if(isValid(x, y) || canDiag(x, y)){
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
            if(isValid(x, y) || canDiag(x, y)){
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
