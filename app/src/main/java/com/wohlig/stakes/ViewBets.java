package com.wohlig.stakes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ViewBets extends AppCompatActivity {

    private String bookName;
    private int bookId;
    private ArrayList<HashMap<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bets);

        Intent thisIntent = getIntent();

        Bundle extras = getIntent().getExtras();
        bookId = extras.getInt("BookId");
        bookName = thisIntent.getStringExtra("BookName");

        getSupportActionBar().setTitle(bookName);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));

        ListView listView = (ListView) findViewById(R.id.lvBet);
        populateList();
        BetListViewAdapter adapter = new BetListViewAdapter(this,list);
        listView.setAdapter(adapter);
    }

    private void populateList(){

        list=new ArrayList<HashMap<String, String>>();
        List<Bet> betList = Bet.findWithQuery(Bet.class, "Select * from Bet where BOOK_ID = ?", Integer.toString(bookId));
        for(Bet b : betList)
        {
            String backLay=null;
            String betID = Long.toString(b.getId());
            int bl = b.getBacklay();
            if(bl==1){
                backLay= "Lay";
            }else{
                backLay= "Back";
            }
            Horse horse = Horse.findById(Horse.class, (long) b.getFavorite());
            Long ts = b.getTimestamp();
            String desc = backLay+" on "+horse.getHorseName()+" @ "+b.getOdds()+" for $"+b.getStake();
            Log.d("Desc",desc);

            Date date = new Date(b.getTimestamp());

            SimpleDateFormat formatter = new SimpleDateFormat("cccc, LLLL F,yyyy hh:mm:ss aaa");
            String dateTime = formatter.format(date);
            Log.d("DateTime", dateTime);

            HashMap<String,String> map = new HashMap<String,String>();
            map.put("tvBetDesc",desc);
            map.put("tvBetTime",dateTime);
            map.put("btId",betID);
            map.put("bl",Integer.toString(bl));
            list.add(map);
        }
    }

    public void deleteBet(int bid){

        int id = bid;
        Log.d("Selected id", Integer.toString(id));

        Bet b = Bet.findById(Bet.class, (long) id);
        //String abc =b.getFavorite();

        Double oddsValue = Double.parseDouble(String.valueOf(b.getOdds()));
        Double stakeValue = Double.parseDouble(String.valueOf(b.getStake()));
        int typeId = b.getBacklay();
        int favHorse = b.getFavorite();
        int bookId = b.getBookId();

        Double result = 0.0;

        Log.d("Result",Double.toString(result));

        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(bookId));

        for (Horse h : horseList){

            int loopHorseId = (int) (long) h.getId();

            //check for selected horse
            if(loopHorseId==favHorse){
                Log.d("Matched",h.getHorseName());

                int temp;
                if(typeId==-1) { //back on Horse selected (Subtract)
                    Log.d("Selected-Back on ",h.getHorseName());
                    temp =-1;
                    result = oddsValue * stakeValue * temp;
                    Log.d("Result: ",Double.toString(result));
                }
                else{ //Lay on Horse Selected (Add)
                    Log.d("Selected-Lay on ",h.getHorseName());
                    temp=1;
                    result=oddsValue*stakeValue*temp;
                    Log.d("Result: ",Double.toString(result));
                }
            }
            else{

                Log.d("Couldn't match", h.getHorseName());
                if(typeId==-1) { //back on other horse //actually lay
                    Log.d("Other-Back on ",h.getHorseName());
                    int temp =1;
                    result=stakeValue*temp;
                    Log.d("Result: ",Double.toString(result));

                }
                else{ // lay on other horse //actually back
                    Log.d("Other-Lay on ",h.getHorseName());
                    int temp=-1;
                    result=stakeValue*temp;
                    Log.d("Result: ",Double.toString(result));
                }
            }

            //save horse total
            Long horseId = h.getId();
            Double total = h.getTotal();
            //total = total - result;
            Horse horse = Horse.findById(Horse.class,horseId);
            horse.total = total - result ;
            horse.save();


            //delete bet in the end
            b.delete();
        }

        // get the list of horses - just for reference
        List<Horse> horses = Horse.listAll(Horse.class);
        for(Horse h:horses){
            String log = null;
            log = h.getHorseName()+ "\t "+h.getBookId()+"\t "+h.getTotal();
            Log.d("",log);
        }

        try
        {
            finish();
        }
        catch(Exception e)
        {

        }

    }

    public void onDeleteBetButton(View v)
    {

        final int btnId = v.getId();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Are you sure?");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        deleteBet(btnId);
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
