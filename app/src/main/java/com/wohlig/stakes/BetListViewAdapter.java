package com.wohlig.stakes;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jay on 03-09-2015.
 */
public class BetListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public BetListViewAdapter(Activity activity,ArrayList<HashMap<String,String>> list){
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
        TextView txtDesc,txtTime;
        ImageButton button;
        LinearLayout linearLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.list_bet_layout,null); //change the name of the layout
            holder=new ViewHolder();

            holder.linearLayout= (LinearLayout) convertView.findViewById(R.id.linerarHorizontalLayout);
            holder.txtDesc= (TextView) convertView.findViewById(R.id.tvBetDesc); //find the different Views
            holder.txtTime= (TextView) convertView.findViewById(R.id.tvBetTime);
            holder.button = (ImageButton) convertView.findViewById(R.id.btDeleteBet);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map =list.get(position);
        holder.txtDesc.setText(map.get("tvBetDesc")); //set the hash maps
        holder.txtTime.setText(map.get("tvBetTime"));
        holder.button.setId(Integer.parseInt(map.get("btId")));
        int backLay = Integer.parseInt(map.get("bl"));
        if(backLay==1){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
            holder.button.setBackgroundColor(Color.parseColor("#4CAF50"));
        }else{
            holder.linearLayout.setBackgroundColor(Color.parseColor("#FF5252"));
            holder.button.setBackgroundColor(Color.parseColor("#FF5252"));
        }
        return convertView;
    }
}