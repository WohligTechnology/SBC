package com.wohlig.jay.stake_betcalculator;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.dd.CircularProgressButton;
import com.rey.material.widget.EditText;

public class NewBook extends AppCompatActivity {

    CircularProgressButton btCreate;
    EditText h1, h2, bn;
    DatePicker dpd;

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
    }

    public void createBook(View v) {

        //fetch all the values
        String name = bn.getText().toString();
        String horse1 = h1.getText().toString();
        String horse2 = h2.getText().toString();
        int day = dpd.getDayOfMonth();
        int month = dpd.getMonth() + 1;
        int year = dpd.getYear();
        String date = day + "/" + month + "/" + year;

        //save the new book
        Book book = new Book(name,date);
        book.save();
        Long bookid = book.getId();
        Log.d("BookId new", Long.toString(bookid));

        //save horses with bookid in horse table
        for(int i=1;i<=2;i++) {
            String horseName=null;

            if(i==1)
                horseName=horse1;
            else
                horseName=horse2;

            Double total=0.0; // initially total for each horse would be 0.0 (it is double so decimal required)
            Horse horse = new Horse((int)(float)bookid,horseName, total); //bookid,horsename,total
            horse.save();

            /*
                need to make it dynamic for multiple horses
            */
        }
        //success button
        btCreate.setIndeterminateProgressMode(true);
        btCreate.setProgress(100);
    }
}
