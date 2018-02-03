package com.example.lolyf.simplerentcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by lolyf on 2/2/2018.
 */


public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Room> rooms;

    public MyAdapter(Context context, ArrayList<Room> rooms){
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public int getCount(){
        return rooms.size();
    }

    @Override
    public Object getItem(int position){
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(rooms.get(position).getName() + " - " + rooms.get(position).getType());
        text2.setText(String.valueOf(rooms.get(position).getSize()));

        return twoLineListItem;
    }
}
