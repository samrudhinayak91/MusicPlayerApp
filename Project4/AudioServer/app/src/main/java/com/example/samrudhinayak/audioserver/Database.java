package com.example.samrudhinayak.audioserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Samrudhi Nayak on 4/6/2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final int version = 1;
    final private Context mContext;
    //name the database and the columns. IDCoulmn is the primary key used to uniquely ID each record in the table
    public static final String DatabaseName = "SongDatabase.db";
    public static final String SongTable = "Songs";
    public static final String IDColumn = "id";
    public static final String DateColumn = "Date";
    public static final String TypeofRequest = "Type_of_Request";
    public static final String ClipNumber = "Clip_Number";
    public static final String State = "State";
    final static String[] columns = { IDColumn, DateColumn,TypeofRequest,ClipNumber,State };


    public Database(Context context)
    {
        super (context,DatabaseName,null,version);
        //get context of the object
        this.mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //to create the table
        db.execSQL("CREATE TABLE " + SongTable + "("
                + IDColumn + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DateColumn + " TEXT,"
                + TypeofRequest + " TEXT,"
                + ClipNumber + " TEXT,"
                + State + " TEXT)");
        Log.d("Sammy says","DB created!!!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    void deleteDatabase() {
        //to delete the database when it the activity is destroyed
        mContext.deleteDatabase(DatabaseName);
    }
}

