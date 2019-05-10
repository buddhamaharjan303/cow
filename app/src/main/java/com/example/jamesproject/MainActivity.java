package com.example.jamesproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

  ArrayList<String>cow;


    static String[] pageNames = {
            "ANGUS",
            "HEREFORD",
            "BRAHMAN",
            "SHORTHORN",
            "BRANGUS"
    };


    private Button angus, hereford, shorthorn;
    private int currentPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.homeFragment();
        cow= new ArrayList <>();
        cow.add("ID");
        cow.add("weight");
        cow.add("age");
        cow.add("condition");
    }

    public void onClick(View view) {
        this.currentPage = Integer.valueOf((String) view.getTag());
        showCurrentPage();
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




