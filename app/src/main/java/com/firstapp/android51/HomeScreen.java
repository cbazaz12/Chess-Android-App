package com.firstapp.android51;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    public static ArrayList<Game> listOfGames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        readFile();
    }

    public void play(View view) {
        Intent intent = new Intent(HomeScreen.this, Chess.class);
        startActivity(intent);
    }

    public void replays(View view) {
        Intent intent = new Intent(HomeScreen.this, GamesList.class);
        startActivity(intent);
    }

    public void readFile(){
        try {
            FileInputStream fis = openFileInput("Save.txt");
            ObjectInputStream is = new ObjectInputStream(fis);
            listOfGames = (ArrayList<Game>)is.readObject();
            is.close();
            fis.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
