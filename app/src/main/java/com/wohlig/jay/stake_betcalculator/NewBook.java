package com.wohlig.jay.stake_betcalculator;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.dd.CircularProgressButton;
import com.rey.material.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class NewBook extends AppCompatActivity {

    CircularProgressButton btCreate;
    EditText h1, h2, bn;
    DatePicker dpd;
    LinearLayout moreHorses;
    int horseNum=2;
    List<EditText> allEt = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        getSupportActionBar().setTitle("New Book");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));

        h1 = (EditText) findViewById(R.id.etHorse1);
        h2 = (EditText) findViewById(R.id.etHorse2);
        bn = (EditText) findViewById(R.id.etBookName);
        dpd = (DatePicker) findViewById(R.id.dpDate);
        btCreate = (CircularProgressButton) findViewById(R.id.btNewBook);
        moreHorses = (LinearLayout) findViewById(R.id.moreHorses);

        allEt.add(h1);
        allEt.add(h2);
    }

    public void createBook(View v) {

        //fetch all the values
        String name = bn.getText().toString();
        int day = dpd.getDayOfMonth();
        int month = dpd.getMonth() + 1;
        int year = dpd.getYear();
        String date = day + "/" + month + "/" + year;

        //save the new book
        Book book = new Book(name,date);
        book.save();
        Long bookid = book.getId();
        Log.d("BookId new", Long.toString(bookid));

        String h=null;
        //save horses with bookid in horse table
        for(int i=0;i<horseNum;i++) {
            String horseName=null;
            h=h+i;
            horseName=allEt.get(i).getText().toString();
            Log.d("a",allEt.get(i).getText().toString());
            if(horseName.isEmpty())
                continue;

            Double total=0.0; // initially total for each horse would be 0.0 (it is double so decimal required)
            Horse horse = new Horse((int)(float)bookid,horseName, total); //bookid,horsename,total
            horse.save();
        }
        allEt.clear();
        //success button
        btCreate.setIndeterminateProgressMode(true);
        btCreate.setProgress(100);
    }

    public void addHorse(View v){

        horseNum++;
        horseNum=horseNum;
        Log.d("After horse", Integer.toString(horseNum));

        LayoutInflater Li = LayoutInflater.from(getApplicationContext());
        View view = Li.inflate(R.layout.et_layout,null);
        EditText myEditText = (EditText) view.findViewById(R.id.et);
        myEditText.setHint("Horse " + horseNum);
        moreHorses.addView(myEditText);
        allEt.add(myEditText);

    }
}
