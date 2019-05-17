package com.example.assignment2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.assignment2.db.DBAdapter;
import com.example.assignment2.model.CowLog;
import com.example.assignment2.model.User;
import com.example.assignment2.util.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> cow;
    private int currentPage;

    public static DBAdapter dbAdapter;

    static String[] pageNames = {
            "ANGUS",
            "HEREFORD",
            "BRAHMAN",
            "SHORTHORN",
            "BRANGUS"
    };

    public static List<CowLog> cowLogs;
    public static User user;

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
        MainActivity.dbAdapter = new DBAdapter(this);
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

        MainActivity.cowLogs = MainActivity.dbAdapter.getAllEntries();
        MainActivity.user    = MainActivity.dbAdapter.getUser();
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
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure? This will delete all the entries.")
                        .setMessage("Save entries first?")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            if (!MainActivity.cowLogs.isEmpty()){
                                sendEmail();
                                MainActivity.dbAdapter.deleteAllEntries();
                                MainActivity.cowLogs.clear();
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .create()
                        .show();


                break;
            case R.id.save:
                int count = 0;
                boolean isFailed =false;
                for (CowLog cowLog: MainActivity.cowLogs) {
                    if (!MainActivity.dbAdapter.isExists(cowLog.getId())){
                        long numberOfRow = MainActivity.dbAdapter.insertEntry(cowLog);
                        if (numberOfRow == 0){
                            isFailed = true;
                            break;
                        }else{
                            count++;
                        }

                    }
                }
                if (!isFailed && count > 0){
                    Toast.makeText(this, "Entry saved in database.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile:
                this.profileFragment();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        StringBuilder body = new StringBuilder();
        body.append(MainActivity.user.getUsername()).append("\n");
        for (CowLog cowLog : MainActivity.cowLogs){
            body.append(cowLog.toEmailString()).append("\n");
        }
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ronbalsys@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "New Logger data");
        i.putExtra(Intent.EXTRA_TEXT   , body.toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onHome(View view){
        this.currentPage = 5;
        showCurrentPage();
    }

    public void onClick(View view) {
        if (view.getTag() == null){
            this.currentPage = 5;
        }else{
            this.currentPage = Integer.valueOf((String) view.getTag());
        }
        showCurrentPage();
    }
    public void addReturn(View view) {
        this.cowFragment();
    }

    public void onPrevious(View view) {
        if (this.currentPage == 0){
            this.currentPage = 4;
        }else{
            this.currentPage--;
        }
        this.cowFragment();
    }

    public void onNext(View view) {
        if (this.currentPage == 4){
            this.currentPage = 0;
        }else{
            this.currentPage++;
        }
        this.cowFragment();
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

    private void profileFragment(){
        ProfileFragment frag = new ProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cowPlace, frag).commit();
    }

    private void showCurrentPage() {
        switch (this.currentPage) {
            case 0:
                this.cowFragment();
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
            case 5:
                this.homeFragment();
                break;
        }

    }
}




