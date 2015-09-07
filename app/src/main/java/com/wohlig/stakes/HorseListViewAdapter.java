package com.wohlig.stakes;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jay on 02-09-2015.
 */
public class HorseListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public HorseListViewAdapter(Activity activity,ArrayList<HashMap<String,String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        //all the fields in layout specified
        TextView txtHorseName,txtHorseTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.list_horse_layout,null); //change the name of the layout
            holder=new ViewHolder();

            holder.txtHorseName= (TextView) convertView.findViewById(R.id.tvHorseName); //find the different Views
            holder.txtHorseTotal= (TextView) convertView.findViewById(R.id.tvHorseTotal);

            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map =list.get(position);
        //Log.d("HorseName",map.get("tvHorseName"));
        //Log.d("HorseTotal", map.get("tvHorseTotal"));
        if(Float.parseFloat(map.get("tvHorseTotal"))>0.0){
            //Log.d("greater",map.get("tvHorseTotal"));
            holder.txtHorseTotal.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        else if(Float.parseFloat(map.get("tvHorseTotal"))==0.0) {
            //Log.d("equal",map.get("tvHorseTotal"));
            holder.txtHorseTotal.setBackgroundColor(Color.parseColor("#0D71FF"));
        }
        else{
            //Log.d("smaller",map.get("tvHorseTotal"));
            holder.txtHorseTotal.setBackgroundColor(Color.parseColor("#FF5252"));
        }
        holder.txtHorseName.setText(map.get("tvHorseName")); //set the hash maps
        holder.txtHorseTotal.setText(map.get("tvHorseTotal"));

        return convertView;
    }
}
