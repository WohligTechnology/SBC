package com.wohlig.stakes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenBook extends AppCompatActivity {

    private int bookId;
    private String bookName;
    private ListView lvHorse;
    private ArrayList<HashMap<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_book);


        lvHorse = (ListView) findViewById(R.id.lvHorse);

        Intent thisIntent = getIntent();

        Bundle extras = getIntent().getExtras();
        bookId = extras.getInt("BookId");

        bookName = thisIntent.getStringExtra("BookName");

        getSupportActionBar().setTitle(bookName);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));

        ListView listView = (ListView) findViewById(R.id.lvHorse);
        populateList();
        HorseListViewAdapter adapter = new HorseListViewAdapter(this,list);
        listView.setAdapter(adapter);


        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(bookId));

        final String[] horse = new String[horseList.size()];
        int i = 0;
        for (Horse h : horseList){
            horse[i]= h.getHorseName();
            Log.d("Horse name",horse[i]);
            i++;
        }

        lvHorse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try
                {
                    // make an intent to open new activity and send the values
                    Intent makeBet = new Intent(OpenBook.this, newBet.class);
                    makeBet.putExtra("horse",horse);
                    makeBet.putExtra("bookName",bookName);
                    makeBet.putExtra("bookId",bookId);
                    makeBet.putExtra("position",position);
                    startActivity(makeBet);
                }
                catch(Exception e)
                {

                }
            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        ListView listView = (ListView) findViewById(R.id.lvHorse);
        populateList();
        HorseListViewAdapter adapter = new HorseListViewAdapter(this,list);
        listView.setAdapter(adapter);

    }

    private void populateList(){

        list=new ArrayList<HashMap<String, String>>();
        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(bookId));
        for(Horse h : horseList)
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("tvHorseName", h.getHorseName());
            map.put("tvHorseTotal",Double.toString(h.getTotal()));
            list.add(map);
        }
    }

    public void viewBets(View v){

        Intent viewBets = new Intent(this,ViewBets.class);
        viewBets.putExtra("BookName", bookName);
        viewBets.putExtra("BookId",bookId);
        startActivity(viewBets);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_open_book, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent viewBets = new Intent(this,ViewBets.class);
        viewBets.putExtra("BookName", bookName);
        viewBets.putExtra("BookId",bookId);
        startActivity(viewBets);

        return super.onOptionsItemSelected(item);
    }
}
