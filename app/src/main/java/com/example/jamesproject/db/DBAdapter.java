package com.example.jamesproject.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jamesproject.model.CowLog;

public class DBAdapter {

    public static final String TABLE_NAME = "cow_logs";

    public static final String COLUMN_ID = "id";
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

    public static  class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBAdapter.CREATE_TABLE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(DBAdapter.DROP_TABLE);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }
}
