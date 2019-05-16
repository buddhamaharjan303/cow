package com.example.assignment2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment2.model.User;

import org.apache.commons.lang3.StringUtils;

public class ProfileFragment extends Fragment {
    private EditText userName,password,repeatPassword;
    private Button profile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view       = inflater.inflate(R.layout.fragment_profile, container, false);
        userName        = getActivity().findViewById(R.id.username);
        password        = getActivity().findViewById(R.id.password);
        repeatPassword  = getActivity().findViewById(R.id.repeatPassword);
        profile.setOnClickListener(v -> {
            String username = userName.getText().toString();
            String pass     = password.getText().toString();
            String repeat   = repeatPassword.getText().toString();
            if (StringUtils.isBlank(username)){
                Toast.makeText(getActivity(),"User name is required.",Toast.LENGTH_LONG).show();
                return;
            }
            if (StringUtils.isBlank(pass)){
                Toast.makeText(getActivity(),"Password is required.",Toast.LENGTH_LONG).show();
                return;
            }
            if (StringUtils.isBlank(repeat)){
                Toast.makeText(getActivity(),"Repeat is required.",Toast.LENGTH_LONG).show();
                return;
            }
            if (!pass.equals(repeat)){
                Toast.makeText(getActivity(),"Password and Repeat should be same.",Toast.LENGTH_LONG).show();
                return;
            }
            MainActivity.user = new User(username,pass);
            long value = MainActivity.dbAdapter.insertUser(MainActivity.user);
            if (value > 0){
                Toast.makeText(getActivity(),"Profile Updated.",Toast.LENGTH_LONG).show();
            }

        });
        if (MainActivity.user !=null){
            userName.setText(MainActivity.user.getUsername());
            password.setText(MainActivity.user.getPassword());
            repeatPassword.setText(MainActivity.user.getPassword());
        }
        return view;
    }

}
