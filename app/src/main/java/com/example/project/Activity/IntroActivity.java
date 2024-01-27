package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.project.R;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startBtn, signupbtn;
    TextView foodshades;
    ImageView flogo;
    float v=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        startBtn = findViewById(R.id.startbtn);
        signupbtn = findViewById(R.id.signupbtn);
        foodshades = findViewById(R.id.fs);
        flogo = findViewById(R.id.flogo);

        startBtn.setTranslationZ(1000);
        signupbtn.setTranslationZ(1000);
        foodshades.setTranslationZ(1000);
        flogo.setRotationY(500);

        startBtn.setAlpha(v);
        signupbtn.setAlpha(v);
        foodshades.setAlpha(v);
        flogo.setAlpha(v);

        foodshades.animate().translationZ(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        flogo.animate().rotationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        startBtn.animate().translationZ(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        signupbtn.animate().translationZ(0).alpha(1).setDuration(1000).setStartDelay(400).start();


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IntroActivity.this, "Login has been clicked", Toast.LENGTH_LONG).show();
                Intent Intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(Intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IntroActivity.this, "Sign Up has been clicked", Toast.LENGTH_LONG).show();
                Intent Intent = new Intent(IntroActivity.this,SignUpActivity.class);
                startActivity(Intent);
            }
        });
    }
}