package com.example.samrudhinayak.audioserver;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CommonPackage.CommonInterface;

public class ServiceProviderActivity extends Service {
    //create an object of the predefined mediaplayer class
    private MediaPlayer mPlayer;
    private int mStartID;
    public int length;
    //list of songs stored in the raw folder
    int[] songs= {R.raw.the_mercury_tale, R.raw.instr1, R.raw.instr2};
    //object of the database class used to insert or retrieve values from the database
    Database helper=new Database(this);
    //content values object used to return the values of the cursor
    ContentValues values = new ContentValues();



    @Override
    public void onCreate() {
        //used to initialize the mediaplayer
        mPlayer=MediaPlayer.create(getApplicationContext(),songs[0]);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //stub to return values to the client. this will contain all the methods from the common interface between the client and the server
        return new CommonInterface.Stub() {
            public void playservice(int tracknum) {
                values=new ContentValues();
                //calls stop to make sure that 2 sngs do not play at the same time
                stopservice(tracknum);
                //used to see if the mediaplayer is currently not playing music
                if(mPlayer==null) {
                    //used since the array is 0 indexed
                    tracknum=tracknum-1;
                    //create the song selected from the list
                    mPlayer = MediaPlayer.create(getApplicationContext(), songs[tracknum]);
                }
                    if (mPlayer.isPlaying()) {
                        //used to start the song over from the beginning if play is pressed while a song is already playing
                        mPlayer.seekTo(0);
                        //used to start the song
                        mPlayer.start();
                        Log.d("sam", "pause/play called");
                    } else {
                        mPlayer.start();
                        Log.d("sam", "play called");
                    }
                //used to get an instance of calendar class to get the current date
                Calendar today = Calendar.getInstance();
                //used to specify the format in which date is to be specified
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                //used to get the current date
                String dateString;
                dateString = dateFormat.format(today.getTime());
                //used to add records to the database
                values.put(helper.DateColumn, "Date: "+dateString);
                values.put(helper.TypeofRequest, "Action: Play");
                values.put(helper.ClipNumber,"Clip number: "+ (tracknum+1));
                values.put(helper.State, "Description: Playing song number " + (tracknum+1));
                //used to insert into the database
                helper.getWritableDatabase().insert(helper.SongTable, null, values);
            }
            public void stopservice(int tracknum)
            {
                values=new ContentValues();
                //check if the player is playing song some, if it is, then stop the song.
                if(mPlayer!=null) {
                    //stops the player
                    mPlayer.stop();
                    mPlayer = null;
                }
                Log.d("sam", "stop called");
                //used to get an instance of calendar class to get the current date
                Calendar today = Calendar.getInstance();
                //used to specify the format in which date is to be specified
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                //used to get the current date
                String dateString;
                dateString = dateFormat.format(today.getTime());
                //used to add records to the database
                values.put(helper.DateColumn, "Date: " + dateString);
                values.put(helper.TypeofRequest, "Action: Stop");
                values.put(helper.ClipNumber, "Clip number: "+tracknum);
                values.put(helper.State, "Description: Stopped while playing song number " + tracknum);
                //used to insert the values into the database
                helper.getWritableDatabase().insert(helper.SongTable, null, values);
            }
            public void pauseservice(int tracknum)
            {
                //pauses the player
                mPlayer.pause();
                //gets the position at which it was paused
                length = mPlayer.getCurrentPosition();
                Log.d("sam", "pause called");
                //used to get an instance of calendar class to get the current date
                Calendar today = Calendar.getInstance();
                //used to specify the format in which date is to be specified
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                //used to get the current date
                String dateString;
                dateString = dateFormat.format(today.getTime());
                //used to add values to the database
                values.put(helper.DateColumn, "Date: " + dateString);
                values.put(helper.TypeofRequest, "Action: Pause");
                values.put(helper.ClipNumber, "Clip number: "+tracknum);
                values.put(helper.State, "Description: Paused while playing song number " + tracknum);
                //used to insert records into the database
                helper.getWritableDatabase().insert(helper.SongTable, null, values);

            }
            public void resumeservice(int tracknum)
            {
                //used to check if the player is currently not playing a song i.e. song has been paused
                if(mPlayer!=null) {
                    //goes to the position where the player had paused
                    mPlayer.seekTo(length);
                    //starts from that position
                    mPlayer.start();
                    Log.d("sam", "resume called");
                    //used to get an instance of calendar class to get the current date
                    Calendar today = Calendar.getInstance();
                    //used to specify the format in which date is to be specified
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    //used to get the current date
                    String dateString;
                    dateString = dateFormat.format(today.getTime());
                    //used to add records to the database
                    values.put(helper.DateColumn, "Date: " + dateString);
                    values.put(helper.TypeofRequest, "Action: Resume");
                    values.put(helper.ClipNumber, "Clip number: "+tracknum);
                    values.put(helper.State, "Description: Resumed after pausing song number " + tracknum);
                    //used to insert values into the database
                    helper.getWritableDatabase().insert(helper.SongTable, null, values);
                }
            }
            public List<String> getlogs()
            {
                //used to create an object of te database class to read the records
                SQLiteDatabase db = helper.getReadableDatabase();
                //used to retreive all the records from the database
                String query = "SELECT * FROM " + helper.SongTable;
                //used to access the values
                Cursor c = db.rawQuery(query, null);
                //list used to pass the database values to the client
                List<String> tempResult = new ArrayList<>();
                if( c.moveToFirst() )
                {
                    //string used to store each transaction detail into a row in the database
                    String collective = "";
                    do
                    {
                        collective = c.getString(1) + ","
                                + c.getString(2) + ","
                                + c.getString(3) + ","
                                + c.getString(4);
                        tempResult.add(collective);
                        Log.d("Sammy",collective);
                    } while( c.moveToNext() );

                }
                return tempResult;

            }
        };
    }

    @Override
    public void onDestroy() {
        //check if song is playing
        if (null != mPlayer) {
            //stop the player and release the resource
            mPlayer.stop();
            mPlayer.release();
            Log.d("sam", "destroy called");
        }
        //close the database
        helper.getWritableDatabase().close();
        //delete the database contents when server is destroyed
        helper.deleteDatabase();
        super.onDestroy();
    }
}



