package com.wohlig.jay.stake_betcalculator;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jay on 02-09-2015.
 */
public class BookListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public BookListViewAdapter(Activity activity,ArrayList<HashMap<String,String>> list){
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
        TextView txtFirst,txtIdHidden;
        ImageButton button;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.list_book_layout,null); //change the name of the layout
            holder=new ViewHolder();

            holder.txtFirst= (TextView) convertView.findViewById(R.id.tvBookName); //find the different Views
            holder.txtIdHidden= (TextView) convertView.findViewById(R.id.tvIdHidden);
            holder.button = (ImageButton) convertView.findViewById(R.id.btDeleteBook);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map =list.get(position);
        holder.txtFirst.setText(map.get("tvBookName")); //set the hash maps
        holder.txtIdHidden.setText(map.get("tvIdHidden"));
        holder.button.setId(Integer.parseInt(map.get("tvIdHidden")));
        //holder.button.setText(map.get("btDeleteButton"));
        return convertView;
    }
}
