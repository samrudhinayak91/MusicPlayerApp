package com.example.samrudhinayak.playerclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samrudhi Nayak on 4/8/2016.
 */
public class Adapter extends BaseAdapter {
    List list= new ArrayList();
    private TextViewData textview[];
    static LayoutInflater layoutInflater;
    public Adapter(Context context, TextViewData col1[]) {
        textview = col1;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //view holder to hold the different text views
    static class ViewHolder{
        TextView dateTV;
        TextView actionTV;
        TextView clipTV;
        TextView descTV;
    }
    //method to get the size of the list
    @Override
    public int getCount() {
        return textview.length;
    }
    //method to get the position of the item on the list
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    //method to get the id of the item
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //method to open the listview based on the layout specified on text_view.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //recycle convertView
        if(convertView==null)
        {
            //inflate the layout specified in text_view.xml
            convertView = layoutInflater.inflate(R.layout.text_view, null);
            //create an instance of the viewholder to populate their entries
            holder=new ViewHolder();
            holder.dateTV= (TextView) convertView.findViewById(R.id.Date);
            holder.actionTV= (TextView) convertView.findViewById(R.id.activity_action);
            holder.clipTV= (TextView) convertView.findViewById(R.id.Clip_Number);
            holder.descTV= (TextView) convertView.findViewById(R.id.desc);
            //save viewholder into memory of convertview
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
        //populate the listview with the elements based on position in list
        holder.dateTV.setText(textview[position].getdate1());
        holder.actionTV.setText(textview[position].getactivity_action());
        holder.clipTV.setText(textview[position].getclip_number());
        holder.descTV.setText(textview[position].getDescription());
        return convertView;
    }
}

