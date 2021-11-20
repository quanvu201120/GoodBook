package com.quanvu201120.goodbooksellers.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.quanvu201120.goodbooksellers.LoginActivity;
import com.quanvu201120.goodbooksellers.R;


public class BlankFragment2 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);

        Button btnBatDau;
        btnBatDau = view.findViewById(R.id.btnBatDau_intro);




        btnBatDau.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("Intro", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Check_Intro",true);
            editor.commit();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        });


        return view;
    }
}