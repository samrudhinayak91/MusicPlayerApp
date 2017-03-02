package com.example.samrudhinayak.playerclient;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Samrudhi Nayak on 4/8/2016.
 */
public class ListDisplayActivity extends Activity {
    Adapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate the listview
        setContentView(R.layout.list_layout);
        ListView listView= (ListView) findViewById(R.id.list);
        //get data passed in intent
        Intent intent=getIntent();
        String[] itemArray=intent.getStringArrayExtra("logs");
        //object of textviewdata which is used to send values that needs to be plugged in
        TextViewData sendData[] = new TextViewData[itemArray.length];
        for (int i = 0; i< itemArray.length; i++)
        {
            //used to split the array based on the , delimiter
            String tempLine[] = itemArray[i].split(",");
            //used to send each of the values i.e. templine[0] represents the date, templine[1] represents the action etc.
            sendData[i] = new TextViewData(tempLine[0], tempLine[1], tempLine[2], tempLine[3]);
        }
        Log.d("Samrudhi", "L2: " + sendData.length);
        //use adapter to plug in the values
        adapter = new Adapter(this, sendData);
        listView.setAdapter(adapter);

    }

}
