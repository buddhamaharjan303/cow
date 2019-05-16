package com.example.jamesproject.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jamesproject.model.CowLog;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {

    public static final String TABLE_NAME = "cow_logs";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME_ENTRY = "time_entry";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_COW_ID = "cow_id";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "logs.db";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TIME_ENTRY + " TEXT,"
            + COLUMN_WEIGHT + " INTEGER,"
            + COLUMN_AGE + " INTEGER,"
            + COLUMN_COW_ID + " INTEGER,"
            + COLUMN_CONDITION + " TEXT"
            + ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final int ID_NEW = -1;

    private DBHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public DBAdapter(Context ctx) {
        mCtx = ctx;
    }

    public DBAdapter open() throws SQLException {
        mDbHelper = new DBHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    public List<CowLog> getAllEntries(){
        List<CowLog> cowLogs    = new ArrayList<>();
        String selectQuery      = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor           = this.mDb.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                String id           = cursor.getString(0);
                String cow          = cursor.getString(1);
                String weight       = cursor.getString(2);
                String age          = cursor.getString(3);
                String condition    = cursor.getString(4);
                String timeEntry    = cursor.getString(5);

                CowLog cowLog       = new CowLog(id,timeEntry,weight,age,condition,cow);
                cowLogs.add(cowLog);

            }while (cursor.moveToNext());
        }
        return cowLogs;
    }
}
