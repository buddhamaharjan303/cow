package com.example.jamesproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class CowListFragment extends ListFragment {
    private ListView listView;
    private Button backBtn;
    private int selectedPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cow_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            this.listView       = getListView();
            this.backBtn        = getActivity().findViewById(R.id.backBtn);
            this.selectedPage   = getArguments().getInt("cow");
            String cowName      = MainActivity.pageNames[this.selectedPage];
            List<String> cowLogs = getArguments().getStringArrayList("list");
            if (cowLogs != null && !cowLogs.isEmpty()){
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,cowLogs);
                // Set The Adapter
                listView.setAdapter(arrayAdapter);
                this.backBtn.setText("Return to "+cowName);
            }
        }
    }


}