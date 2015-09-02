package com.wohlig.jay.stake_betcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.app.Dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvBookId;

    private ArrayList<HashMap<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.lvBook);
        populateList();
        BookListViewAdapter adapter = new BookListViewAdapter(this,list);
        listView.setAdapter(adapter);


        //dont show the app title
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Stake");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));

        //set the title
        /*getSupportActionBar().setTitle("Stake");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));
        getSupportActionBar().;*/

        /*//custom title name and gravity center
        title = (TextView) findViewById(R.id.tvTitle);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        //title.setText("Stake");
        Log.d("Id", Integer.toString(title.getId()));
        //Log.d("text",Character.toString(title.getText()));

        //.setBackgroundDrawable(new ColorDrawable("COLOR"));*/


        /*


        bookListView = (ListView) findViewById(R.id.lvBook);


        List<Book> bookList = Book.listAll(Book.class);

        String[] bookName = new String[bookList.size()];
        int size=0;
        String log=null;
        String temp=null;

        for(Book b : bookList)
        {
            temp = log;
            bookName[size]= b.getBookName();
            log = "\n"+bookName[size];
            Log.d("Name: ", bookName[size]);
            log=temp+log;
            size++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, bookName);

        bookListView.setAdapter(adapter);

        */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;
                String bookname =null;
                String bi=null;

                int bid;

                HashMap<String,String> map =(HashMap<String,String>)parent.getItemAtPosition(position);

                //String value = map.get(TAG_SUCCESS);
                //bookname = (String) parent.getItemAtPosition(position);
                bookname = map.get("tvBookName");
                bid = Integer.parseInt(map.get("tvIdHidden"));

                Log.d("Bookname", bookname);

                try
                {
                    Intent bookIntent = new Intent(MainActivity.this, OpenBook.class);
                    bookIntent.putExtra("BookName", bookname);
                    bookIntent.putExtra("BookId",bid);
                    startActivity(bookIntent);
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

        ListView listView = (ListView) findViewById(R.id.lvBook);
        populateList();
        BookListViewAdapter adapter = new BookListViewAdapter(this,list);
        listView.setAdapter(adapter);

    }

    public void newBook(View view)
    {
        Intent newBookIntent = new Intent(MainActivity.this, NewBook.class);
        startActivity(newBookIntent);
    }

    private void populateList(){

        list=new ArrayList<HashMap<String, String>>();
        List<Book> bookList = Book.listAll(Book.class);
        for(Book b : bookList)
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("tvBookName", b.getBookName());
            map.put("tvIdHidden",Long.toString(b.getId()));
            list.add(map);
        }
    }

    public void onButton(View v){

        tvBookId = (TextView) findViewById(R.id.tvIdHidden);

        int bid = Integer.parseInt(tvBookId.getText().toString());

        try
        {
            Intent bookIntent = new Intent(MainActivity.this, OpenBook.class);
            bookIntent.putExtra("BookId",bid);
            startActivity(bookIntent);
        }
        catch(Exception e)
        {

        }
    }

    public void onDelete(View v){

        int butId =v.getId();
        Log.d("ButtonId",Integer.toString(butId));

        List<Bet> betList = Bet.findWithQuery(Bet.class, "Select * from Bet where BOOK_ID = ?", Integer.toString(butId));
        for(Bet b : betList){
            Long gid = 0L;
            gid = b.getId();
            Log.d("Bet ",Long.toString(gid));
            //Bet bet = Bet.findById(Bet.class,gid);
            //bet.delete();
        }

        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(butId));
        for(Horse h : horseList){
            Long gid = 0L;
            gid = h.getId();
            Log.d("horse ",Long.toString(gid));
            //Horse horse = Horse.findById(Horse.class,gid);
            //horse.delete();
        }
        Book book = Book.findById(Book.class,(long) butId);
        Log.d("Book Name: ", book.getBookName());

    }
    /*public void onDelete(View v){


        //delete all bets of book
        List<Bet> betList = Bet.findWithQuery(Bet.class, "Select * from Bet where BOOK_ID = ?", Integer.toString(id));
        for(Bet b : betList){
            Long gid = 0L;
            gid = b.getId();
            Bet bet = Bet.findById(Bet.class,gid);
            bet.delete();
        }

        //delete all horses of book
        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(id));
        for(Horse h : horseList){
            Long gid = 0L;
            gid = h.getId();
            Horse horse = Horse.findById(Horse.class,gid);
            horse.delete();
        }

        Book book = Book.findById(Book.class,id);
        book.delete();

    }*/

    public void open(View v)
    {
        //Intent open = new Intent(this, Main2Activity.class);
        //startActivity(open);

        int styleId=1;
        Dialog mDialog = new Dialog(this, styleId);
        mDialog.applyStyle(styleId)
                .title("Are you sure you want to delete the book?")
                .positiveAction("OK")
                .negativeAction("CANCEL")
                //.contentView(findViewById(R.id.trial))
                .cancelable(true)
                .show();
    }
}
