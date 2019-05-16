package com.example.assignment2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment2.model.CowLog;
import com.example.assignment2.model.User;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {

    public static final String COW_LOG_TABLE_NAME   = "cow_logs";
    public static final String USER_TABLE_NAME      = "users";


    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";

    public static final String CL_COLUMN_ID         = "_id";

    public static final String CL_COLUMN_TIME_ENTRY = "entry_time";
    public static final String CL_COLUMN_WEIGHT     = "weight";
    public static final String CL_COLUMN_AGE        = "age";
    public static final String CL_COLUMN_CONDITION  = "condition";
    public static final String CL_COLUMN_COW        = "cow";
    public static final String CL_COLUMN_LATITUDE   = "latitude";
    public static final String CL_COLUMN_LONGITUDE  = "longitude";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION        = 1;
    public static final String DATABASE_NAME        = "logs.db";

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
        String selectQuery      = "SELECT * FROM " + COW_LOG_TABLE_NAME;
        Cursor cursor           = this.mDb.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                String id           = cursor.getString(0);
                String cow          = cursor.getString(1);
                String weight       = cursor.getString(2);
                String age          = cursor.getString(3);
                String condition    = cursor.getString(4);
                String timeEntry    = cursor.getString(5);
                String latitude     = cursor.getString(6);
                String longitude    = cursor.getString(7);
                CowLog cowLog       = new CowLog(id,timeEntry,weight,age,condition,cow,latitude,longitude);
                cowLogs.add(cowLog);

            }while (cursor.moveToNext());
        }
        this.close();
        return cowLogs;
    }

    public User getUser(){
        User user = null;
        this.open();
        Cursor cursor = this.mDb.rawQuery("SELECT * from user LIMIT 1",null);
        if (cursor.moveToFirst()){
            String username = cursor.getColumnName(1);
            String password = cursor.getColumnName(2);
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
                user = new User(username,password);
            }

        }
        this.close();
        return user;
    }

    public long insertUser(User user) {
        this.open();
        this.mDb.delete(USER_TABLE_NAME,null,null);
        ContentValues args = new ContentValues();
        args.put(USER_COLUMN_USERNAME,user.getUsername());
        args.put(USER_COLUMN_PASSWORD,user.getPassword());
        long value = mDb.insert(USER_TABLE_NAME,null,args);
        this.close();
        return  value;
    }
    public long insertEntry(CowLog cowLog) {
        this.open();
        ContentValues args = this.toContentValues(cowLog);
        long value =  mDb.insert(COW_LOG_TABLE_NAME, null, args);
        this.close();
        return  value;
    }

    public final ContentValues toContentValues(CowLog cowLog) {
        ContentValues args = new ContentValues();
        args.put(CL_COLUMN_ID, cowLog.getId());
        args.put(CL_COLUMN_AGE, cowLog.getAge());
        args.put(CL_COLUMN_CONDITION, cowLog.getCondition());
        args.put(CL_COLUMN_COW,cowLog.getCowName());
        args.put(CL_COLUMN_TIME_ENTRY, cowLog.getTimeEntry());
        args.put(CL_COLUMN_WEIGHT,cowLog.getWeight());
        args.put(CL_COLUMN_LATITUDE,cowLog.getLatitude());
        args.put(CL_COLUMN_LONGITUDE,cowLog.getLongitude());
        return args;
    }

    public void deleteAllEntries() {
        this.open();
        this.mDb.delete(COW_LOG_TABLE_NAME,null,null);
        this.close();
    }
}
