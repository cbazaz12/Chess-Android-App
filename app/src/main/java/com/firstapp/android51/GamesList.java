package com.firstapp.android51;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GamesList extends AppCompatActivity{

    public static ArrayList<DrawableArray> allBoards;
    public static String result;
    public List<Game> list;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameslist);

        listView = findViewById(R.id.listGames);
        list = new ArrayList<>();
        for(Game g: HomeScreen.listOfGames){
            list.add(g);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allBoards = list.get(position).getBoards();
                result = list.get(position).getGameResult();
                Intent intent = new Intent(GamesList.this, Replay.class);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(GamesList.this, HomeScreen.class);
        startActivity(intent);
    }

    public void sortByName(View view) {
        Collections.sort(list, new Comparator<Game>() {
            public int compare(Game g1, Game g2){
                return g1.toString().compareTo(g2.toString());
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
    }

    public void sortByDate(View view) {
        Collections.sort(list, new Comparator<Game>() {
            public int compare(Game g1, Game g2){
                return Long.compare(g1.getDate(), g2.getDate());
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
    }
}
