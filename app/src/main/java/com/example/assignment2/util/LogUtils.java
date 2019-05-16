package com.example.assignment2.util;

import android.util.Log;

public class LogUtils {
    private final static String COW_LOG = "cow_log";
    public static void info(String message){
        Log.d(COW_LOG,message);
    }
}
