package com.wohlig.jay.stake_betcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;
                String bookname = null;
                String bi = null;

                int bid;

                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);

                bookname = map.get("tvBookName");
                bid = Integer.parseInt(map.get("tvIdHidden"));

                Log.d("Bookname", bookname);

                try {
                    Intent bookIntent = new Intent(MainActivity.this, OpenBook.class);
                    bookIntent.putExtra("BookName", bookname);
                    bookIntent.putExtra("BookId", bid);
                    startActivity(bookIntent);
                } catch (Exception e) {

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

    public void newBook(View view) //new book is used
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

    public void onDelete(int id){

        int butId =id;
        Log.d("ButtonId",Integer.toString(butId));

        List<Bet> betList = Bet.findWithQuery(Bet.class, "Select * from Bet where BOOK_ID = ?", Integer.toString(butId));
        for(Bet b : betList){
            Long gid = 0L;
            gid = b.getId();
            Log.d("Bet ",Long.toString(gid));
            Bet bet = Bet.findById(Bet.class,gid);
            bet.delete();
        }

        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(butId));
        for(Horse h : horseList){
            Long gid = 0L;
            gid = h.getId();
            Log.d("horse ",Long.toString(gid));
            Horse horse = Horse.findById(Horse.class,gid);
            horse.delete();
        }
        Book book = Book.findById(Book.class,(long) butId);
        Log.d("Book Name: ", book.getBookName());
        book.delete();
        onRestart();

    }

    public void open(View v)
    {
        final int btnId = v.getId();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Are you sure?");

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        onDelete(btnId);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

