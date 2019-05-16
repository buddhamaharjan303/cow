package com.example.jamesproject.db;

import android.content.ContentValues;
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
    public static final String COLUMN_TIME_ENTRY = "entry_time";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_COW = "cow";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "logs.db";

    public static final int ID_NEW = -1;

    private DBHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public DBAdapter(Context ctx) {
        this.mCtx = ctx;
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
        this.open();
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
        this.close();
        return cowLogs;
    }

    public long insertEntry(CowLog cowLog) {
        this.open();
        ContentValues args = this.toContentValues(cowLog);
        long value =  mDb.insert(TABLE_NAME, null, args);
        this.close();
        return  value;
    }

    public final ContentValues toContentValues(CowLog cowLog) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, cowLog.getId());
        args.put(COLUMN_AGE, cowLog.getAge());
        args.put(COLUMN_CONDITION, cowLog.getCondition());
        args.put(COLUMN_COW,cowLog.getCowName());
        args.put(COLUMN_TIME_ENTRY, cowLog.getTimeEntry());
        args.put(COLUMN_WEIGHT,cowLog.getWeight());
        return args;
    }
}
