package com.firstapp.android51;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Replay extends AppCompatActivity {

    public ArrayList<DrawableArray> allBoards;
    public String result;
    public TextView resultText;
    public int moveNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);
        allBoards = GamesList.allBoards;
        result = GamesList.result;
        resultText = findViewById(R.id.resultOfGame);
    }
    public void prev(View view) {
        resultText.setText("");
        moveNumber--;
        if(allBoards.size() < 1 || moveNumber < 0){
            moveNumber++;
            return;
        }
        int[][] current = allBoards.get(moveNumber).getAllDrawables();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                String buttonId = "imageButton" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                ImageButton add = findViewById(resId);
                add.setImageResource(current[i][j]);
            }
        }
    }

    public void next(View view) {
        moveNumber++;
        if(allBoards.size() < 1 || moveNumber >= allBoards.size()){
            moveNumber--;
            return;
        }
        int[][] current = allBoards.get(moveNumber).getAllDrawables();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                String buttonId = "imageButton" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                ImageButton add = findViewById(resId);
                add.setImageResource(current[i][j]);
            }
        }
        if(moveNumber == allBoards.size()-1){
            resultText.setText(result);
        }
    }

    public void exit(View view) {
        Intent intent = new Intent(Replay.this, GamesList.class);
        startActivity(intent);
    }
}
