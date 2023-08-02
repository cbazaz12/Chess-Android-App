package com.firstapp.android51;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class SaveGameScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savegame);
        TextView t = findViewById(R.id.gameResult);
        t.setText(Chess.result+"!");
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(SaveGameScreen.this, HomeScreen.class);
        startActivity(intent);
    }

    public void save(View view) {
        EditText n = (EditText)findViewById(R.id.gameName);
        ArrayList<DrawableArray> d = createDrawableArrays(Chess.listOfBoards);
        if(n.getText().toString().matches("")){
            Chess.calendar = Calendar.getInstance();
            Game g = new Game(d, "Untitled Game", Chess.result, Chess.calendar.getTimeInMillis());
            HomeScreen.listOfGames.add(g);
        }
        else{
            Chess.calendar = Calendar.getInstance();
            Game g = new Game(d, n.getText().toString(), Chess.result, Chess.calendar.getTimeInMillis());
            HomeScreen.listOfGames.add(g);
        }
        writeToFile();
        Intent intent = new Intent(SaveGameScreen.this, HomeScreen.class);
        startActivity(intent);
    }

    public ArrayList<DrawableArray> createDrawableArrays(ArrayList<Board> boards){
        ArrayList<DrawableArray> d = new ArrayList<>();
        for(Board b : boards){
            Tile[][] board = b.getBoard();
            int[][] drawables = new int[8][8];
            for(int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].getPiece() == null) {
                        drawables[i][j] = 0;
                    } else {
                        drawables[i][j] = board[i][j].getPiece().getDrawable();
                    }
                }
            }
            DrawableArray add = new DrawableArray(drawables);
            d.add(add);
        }
        return d;
    }
    public void writeToFile(){
        try {
            File f = new File(SaveGameScreen.this.getFilesDir().getAbsolutePath());
            f.delete();
            FileOutputStream fos = openFileOutput("Save.txt", MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(HomeScreen.listOfGames);
            os.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
