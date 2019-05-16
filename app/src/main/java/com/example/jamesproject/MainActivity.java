package com.example.jamesproject;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jamesproject.db.DBAdapter;
import com.example.jamesproject.model.CowLog;
import com.example.jamesproject.util.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> cow;
    public static List<CowLog> cowLogs;
    private Button angus, hereford, shorthorn;
    private int currentPage;

    private DBAdapter dbAdapter;

    static String[] pageNames = {
            "ANGUS",
            "HEREFORD",
            "BRAHMAN",
            "SHORTHORN",
            "BRANGUS"
    };

    public MainActivity() {
        this.cowLogs        = new ArrayList<>();
        this.currentPage    = 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.homeFragment();
        cow = new ArrayList<>();
        cow.add("ID");
        cow.add("weight");
        cow.add("age");
        cow.add("condition");
        // copy database
        dbAdapter = new DBAdapter(this);
        try {
            String destinationPath  = "/data/data/"+getPackageName()+"/databases";
            File file               = new File(destinationPath);
            if (!file.exists()){ //create dir and copy db
                file.mkdirs();
                file.createNewFile();
                copyDB(getBaseContext().getAssets().open("logs.db"),
                        new FileOutputStream(destinationPath+"/logs.db"));
            }else{
                LogUtils.info("Already have database");
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        MainActivity.cowLogs = dbAdapter.getAllEntries();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.send:
                break;
            case R.id.save:
                for (CowLog cowLog: MainActivity.cowLogs) {
                    long numberOfRow = dbAdapter.insertEntry(cowLog);
                    if (numberOfRow == 0){
                        break;
                    }
                }
                break;
            case R.id.profile:
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View view) {
        this.currentPage = Integer.valueOf((String) view.getTag());
        showCurrentPage();
    }
    public void addReturn(View view) {
        this.cowFragment();
    }

    private void showCurrentPage() {
        switch (this.currentPage) {
            case 0:
                this.cowFragment();
                break;
            case 5:
                this.homeFragment();
                break;
            case 1:
                this.cowFragment();
                break;
            case 2:
                this.cowFragment();
                break;
            case 3:
                this.cowFragment();
                break;
            case 4:
                this.cowFragment();
                break;
        }

    }


    public void homeFragment() {
        HomeFragment frag = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.cowPlace, frag);
        transaction.commit();
    }

    private void cowFragment() {
        CowFragment frag = new CowFragment();
        Bundle args = new Bundle();
        args.putInt("cow", currentPage);
        frag.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cowPlace, frag).commit();
    }

    private void copyDB(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        //copu 1k byte at a time
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) >0){
            fileOutputStream.write(buffer,0,length);
        }
        inputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}




