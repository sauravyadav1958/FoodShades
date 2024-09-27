package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.cuberto.liquid_swipe.LiquidPager;
import com.example.project.R;
import com.example.project.liquidpages.viewPager;

public class SplashActivity extends AppCompatActivity {

    ImageView splash, ologo;
    TextView app_name;
    LottieAnimationView lottieAnimationView;
    viewPager viewPager;
    LiquidPager pager;
    Handler h = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


//        ologo = findViewById(R.id.ologo);
//        splash = findViewById(R.id.splash);
//        app_name = findViewById(R.id.app_name);
//        lottieAnimationView = findViewById(R.id.lottie);
//        pager = findViewById(R.id.pager);
//
//
//        splash.animate().translationY(-2100).setDuration(1000).setStartDelay(4000);
//        ologo.animate().translationY(2200).setDuration(1000).setStartDelay(4000);
//        app_name.animate().translationY(2200).setDuration(1000).setStartDelay(4000);
//        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

//        viewPager = new viewPager(getSupportFragmentManager(), 1);
//        pager.setAdapter(viewPager);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

}

