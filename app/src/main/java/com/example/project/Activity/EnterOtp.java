package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOtp extends AppCompatActivity {

    EditText num1, num2, num3, num4, num5, num6;
    String getotpbackend;

    //*MADE WITH LOVE BY ADESH
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        final Button otp = findViewById(R.id.submitotpbtn);

        num1 = findViewById(R.id.otp1);
        num2 = findViewById(R.id.otp2);
        num3 = findViewById(R.id.otp3);
        num4 = findViewById(R.id.otp4);
        num5 = findViewById(R.id.otp5);
        num6 = findViewById(R.id.otp6);

        TextView textView = findViewById(R.id.yournumber);
        textView.setText(String.format(
                "+91-%s", getIntent().getStringExtra("Mobile")
        ));

        getotpbackend = getIntent().getStringExtra("backendotp");
        final  ProgressBar progressBarotp =findViewById(R.id.progressbar_otp);

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(num1.getText().toString().trim().isEmpty() || num2.getText().toString().trim().isEmpty()
                        || num3.getText().toString().trim().isEmpty() ||
                        num4.getText().toString().trim().isEmpty() ||
                        num5.getText().toString().trim().isEmpty() ||
                        num6.getText().toString().trim().isEmpty())) {

                    String entercodeotp = num1.getText().toString() +
                            num2.getText().toString() +
                            num3.getText().toString() +
                            num4.getText().toString() +
                            num5.getText().toString() +
                            num6.getText().toString();

                    if (getotpbackend!=null){
                        progressBarotp.setVisibility(View.VISIBLE);
                        otp.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend, entercodeotp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarotp.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(EnterOtp.this, "Enter the correct otp", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });

                    }else {
                        Toast.makeText(EnterOtp.this, "Please check internet connection", Toast.LENGTH_LONG).show();
                    }


                    //                  Toast.makeText(EnterOtp.this, "OTP verify", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EnterOtp.this, "Please enter all number", Toast.LENGTH_LONG).show();
                }
            }

        });
        numberotpmove();

        findViewById(R.id.resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        EnterOtp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(EnterOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackendotp;
                                Toast.makeText(EnterOtp.this, "OTP sended successfully", Toast.LENGTH_LONG).show();
                            }

                        });
            }

        });
    }


    private void numberotpmove() {
        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    num2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    num3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    num4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    num5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    num6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}