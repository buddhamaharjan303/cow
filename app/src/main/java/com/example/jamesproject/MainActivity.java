package com.example.jamesproject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jamesproject.model.CowLog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> cow;
    public static List<CowLog> cowLogs;
    private Button angus, hereford, shorthorn;
    private int currentPage;

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
        this.homeFragment();
        cow = new ArrayList<>();
        cow.add("ID");
        cow.add("weight");
        cow.add("age");
        cow.add("condition");
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



}




