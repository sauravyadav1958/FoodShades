package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;

public class ProfileActivity extends AppCompatActivity {

    Button logout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView mGoBackBtn = findViewById(R.id.cartBackBtn);
        mGoBackBtn.setOnClickListener(view -> {
            this.onBackPressed();
        });

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO erase customer data from the app before logout -> is it working ?
                Checkout.clearUserData(getApplicationContext());
                auth.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
