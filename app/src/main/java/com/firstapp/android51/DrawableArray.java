package com.firstapp.android51;

import java.io.Serializable;

public class DrawableArray implements Serializable {
    public int[][] allDrawables;

    public DrawableArray(int[][] allDrawables){
        this.allDrawables = allDrawables;
    }

    public int[][] getAllDrawables(){
        return allDrawables;
    }
}
