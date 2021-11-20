package com.quanvu201120.goodbook.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quanvu201120.goodbook.LoginActivity;
import com.quanvu201120.goodbook.R;


public class BlankFragment3 extends Fragment {

    Button btnBatDau;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    }
}