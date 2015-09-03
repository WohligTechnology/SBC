package com.wohlig.jay.stake_betcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dd.CircularProgressButton;

import com.rey.material.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class newBet extends AppCompatActivity {

    Spinner spinHorse,spinBetType;
    EditText odds, stake;
    String name;
    int id;
    CircularProgressButton btCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bet);

        Intent thisIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        String[] l = extras.getStringArray("horse");
        Log.d("length",Integer.toString(l.length));

        name = thisIntent.getStringExtra("bookName");
        id = extras.getInt("bookId");
        int position = extras.getInt("position");
        Log.d("name", name);
        Log.d("id", Integer.toString(id));

        //toolbar color
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));

        spinHorse = (Spinner) findViewById(R.id.spinHorseList);
        spinBetType = (Spinner) findViewById(R.id.spinType);
        odds = (EditText) findViewById(R.id.etOdds);
        stake = (EditText) findViewById(R.id.etStake);
        btCreate = (CircularProgressButton) findViewById(R.id.btCreateBet);


        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(id));
        List<String> betType = new ArrayList<>();

        final String[] horse = new String[horseList.size()];
        final int[] horseId = new int[horseList.size()];
        int i = 0;
        for (Horse h : horseList){
            horse[i]= h.getHorseName();
            horseId[1]= (int) (long) h.getId();
            Log.d("Horse name",horse[i]);
            i++;
        }

        final ArrayAdapter<String> adapterHorse = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,horse);

        spinHorse.setAdapter(adapterHorse);
        spinHorse.setSelection(position);

        betType.add(0,"Back");
        betType.add(1,"Lay");
        final ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,betType);
        spinBetType.setAdapter(adapterType);

    }

    public void createBet(View v){

        Double oddsValue = Double.parseDouble(odds.getText().toString());
        Double stakeValue = Double.parseDouble(stake.getText().toString());
        String teamValue = spinHorse.getSelectedItem().toString();
        //String typeValue = spinBetType.getSelectedItem().toString();
        int typeId = spinBetType.getSelectedItemPosition();
        int horsePosition = spinHorse.getSelectedItemPosition();

        Double result = 0.0;

        Log.d("Result",Double.toString(result));

        List<Horse> horseList = Horse.findWithQuery(Horse.class, "Select * from Horse where BOOK_ID = ?", Integer.toString(id));

        int i = 0;
        for (Horse h : horseList){

            //check for selected horse
            if(i==horsePosition){
                Log.d("Matched",h.getHorseName());

                int temp;
                if(typeId==0) { //back on Horse selected (Subtract)
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

                //save bet
                int horseId=(int) (long) h.getId();
                Bet bet = new Bet(id, horseId, temp, stakeValue, oddsValue,System.currentTimeMillis());
                bet.save();

            }
            else{

                Log.d("Couldn't match", h.getHorseName());
                if(typeId==0) { //back on other horse //actually lay
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
            Horse horse = Horse.findById(Horse.class,horseId);
            horse.total = total + result ;
            horse.save();

            i++;
        }

        // get the list of horses - just for reference
        List<Horse> horses = Horse.listAll(Horse.class);
        for(Horse h:horses){
            String log = null;
            log = h.getHorseName()+ "\t "+h.getBookId()+"\t "+h.getTotal();
            Log.d("",log);
        }

        btCreate.setIndeterminateProgressMode(true);
        btCreate.setProgress(100);

        try
        {
            finish();
        }
        catch(Exception e)
        {

        }
    }
}
