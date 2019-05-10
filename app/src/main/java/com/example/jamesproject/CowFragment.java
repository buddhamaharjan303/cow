package com.example.jamesproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class CowFragment extends Fragment {
    Button save, show;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cow, container, false);

            int Cow = getArguments().getInt("cow");
            TextView cowLabel = (TextView) view.findViewById(R.id.tx1);
            cowLabel.setText(MainActivity.pageNames[Cow]);


        return view;


    }
    public void btnAdd()
    {
        save=(Button)getActivity().findViewById(R.id.Btn_add);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                Date date = calendar.getTime();


            }
        });
    }
    public  void btnShow()
    {
        show=(Button)getActivity().findViewById(R.id.Btn_show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.Btn_add);
        getActivity().findViewById(R.id.Btn_show);
        getActivity().findViewById(R.id.tx1);
        getActivity().findViewById(R.id.spiner1);

    }
}