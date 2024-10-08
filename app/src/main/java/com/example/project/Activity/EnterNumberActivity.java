package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterNumberActivity extends AppCompatActivity {

    EditText enternumber;
    Button getotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enternumber);

        enternumber = findViewById(R.id.enter_number);
        getotp = findViewById(R.id.getotpbtn);

        final ProgressBar progressBar = findViewById(R.id.sendind_otp);

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enternumber.getText().toString().trim().isEmpty()) {
                    if ((enternumber.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        getotp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                EnterNumberActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                        Toast.makeText(EnterNumberActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), EnterOtp.class);
                                        intent.putExtra("Mobile", enternumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );

                    } else {
                        Toast.makeText(EnterNumberActivity.this, "Please enter correct number", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EnterNumberActivity.this, "Enter Mobile Number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}