package com.example.assignment2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2.model.CowLog;
import com.example.assignment2.model.TrackGPS;
import com.example.assignment2.util.LogUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class CowFragment extends Fragment {
    private TrackGPS gps;
    private TextView cowName;
    private EditText idEditTxt,weightEditTxt,ageEditTxt;
    private Button saveBtn, showBtn;
    private Spinner conditionSpinner;
    private int selectedCowNameId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cow, container, false);
        if (getArguments()!=null){
            selectedCowNameId = getArguments().getInt("cow");
            cowName = (TextView) view.findViewById(R.id.cowName);
            cowName.setText(MainActivity.pageNames[selectedCowNameId]);
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.saveBtn                    = getActivity().findViewById(R.id.addBtn);
        this.showBtn                    = getActivity().findViewById(R.id.showBtn);
        this.cowName                    = getActivity().findViewById(R.id.cowName);
        this.idEditTxt                  = getActivity().findViewById(R.id.idEditTxt);
        this.weightEditTxt              = getActivity().findViewById(R.id.weightEditTxt);
        this.ageEditTxt                 = getActivity().findViewById(R.id.ageEditTxt);
        this.conditionSpinner           = getActivity().findViewById(R.id.conditionSpinner);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar   = Calendar.getInstance();
                int year            = calendar.get(Calendar.YEAR);
                int month           = calendar.get(Calendar.MONTH) + 1;
                int days            = calendar.get(Calendar.DAY_OF_MONTH);
                int hours           = calendar.get(Calendar.HOUR);
                int minutes         = calendar.get(Calendar.MINUTE);
                int seconds         = calendar.get(Calendar.SECOND);
                String id           = idEditTxt.getText().toString();
                String weight       = weightEditTxt.getText().toString();
                String age          = ageEditTxt.getText().toString();
                String condition    = conditionSpinner.getSelectedItem().toString();

                String timeEntry    = String.format(Locale.ENGLISH,"%d/%d/%d %d:%d:%d",
                        days,
                        month,
                        year,
                        hours,
                        minutes,
                        seconds);
                LogUtils.info("Time entry "+timeEntry);

                if (StringUtils.isBlank(id)){
                    Toast.makeText(getActivity(),"Id is required. Please place id and try again",Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringUtils.isBlank(weight)){
                    Toast.makeText(getActivity(),"Weight is required. Please place id and try again",Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringUtils.isBlank(age)){
                    Toast.makeText(getActivity(),"Age is required. Please place id and try again",Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringUtils.isBlank(condition)){
                    Toast.makeText(getActivity(),"Condition is required. Please place id and try again",Toast.LENGTH_LONG).show();
                    return;
                }
                CowLog cowLog = new CowLog(id,timeEntry,weight,age,condition,cowName.getText().toString());
                MainActivity.cowLogs.add(cowLog);

                idEditTxt.setText("");
                weightEditTxt.setText("");
                ageEditTxt.setText("");
                conditionSpinner.getItemAtPosition(0);
                Toast.makeText(getActivity(),"Entry saved",Toast.LENGTH_LONG).show();
            }
        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> showLogs = new ArrayList<>();
                for (CowLog cowLog : MainActivity.cowLogs){
                    if (cowLog.getCowName().equalsIgnoreCase(cowName.getText().toString())){
                        showLogs.add(cowLog.toString());
                    }
                }
                if (showLogs.isEmpty()){
                    Toast.makeText(getActivity(),"Empty Log",Toast.LENGTH_LONG).show();
                }else{
                    showLogListFragment(showLogs);
                }
            }
        });

        ArrayAdapter spinnerAdapten     = ArrayAdapter.createFromResource(getActivity(),
                R.array.conditions,
                android.R.layout.simple_spinner_dropdown_item);

        this.conditionSpinner.setAdapter(spinnerAdapten);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.gps            = new TrackGPS(getContext());
        boolean isTracked   = this.gps.canGetLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.gps.stopUsingGPS();
    }

    public void showLogListFragment(ArrayList<String> list) {
        CowListFragment cowListFragment = new CowListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("list", list);
        args.putInt("cow",this.selectedCowNameId);
        cowListFragment.setArguments(args);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cowPlace, cowListFragment).commit();
    }


}