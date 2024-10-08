package com.example.project.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    private Button phoneNoBtn;

    Button googleBtn;
    Button btnlogin;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword2;


    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;

    //*MADE WITH LOVE BY ADESH

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword2 = (EditText) findViewById(R.id.editTextTextPassword2);
        btnlogin = findViewById(R.id.btnlogin);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
        auth = FirebaseAuth.getInstance();


        phoneNoBtn = findViewById(R.id.phonenumberbtn);
        //  auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");


        googleBtn = findViewById(R.id.btnGoogle);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });

        binding.txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


        phoneNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login with otp", Toast.LENGTH_LONG).show();
                Intent Intent = new Intent(LoginActivity.this, EnterNumberActivity.class);
                startActivity(Intent);
            }
        });

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                        binding.editTextTextPassword2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }

    private void sign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (!task.isSuccessful()) {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG);
            }
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);

            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent Intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(Intent);
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }
//        if (auth.getCurrentUser() != null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//
//        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}


