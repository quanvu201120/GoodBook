package com.quanvu201120.goodbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.quanvu201120.goodbook.intro.ViewPagerIntroAdapter;

public class IntroActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ViewPagerIntroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager2 = findViewById(R.id.viewPagerIntro);

        SharedPreferences checkIntro = getSharedPreferences("Intro", Context.MODE_PRIVATE);
        if (checkIntro.getBoolean("Check_Intro",false) == true){
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        adapter = new ViewPagerIntroAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(adapter);

    }
}