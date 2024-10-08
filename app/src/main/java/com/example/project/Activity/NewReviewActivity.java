package com.example.project.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.project.R;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.hsalf.smileyrating.SmileyRating;

import java.util.HashMap;

public class NewReviewActivity extends AppCompatActivity {

    private String uid;
    private String userName;
    private String userImage;
    private String recommendText;
    private String resUid;
//    private SmileyRating ratingBar;
    private EditText mReviewEditText;
    private RadioButton mRecommendBtn,mNotRecommendBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        init();
//        fetchUserDetails();

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        uid = getIntent().getStringExtra("UID");
        db = FirebaseFirestore.getInstance();
//        ratingBar = findViewById(R.id.smiley_rating);
        mReviewEditText = findViewById(R.id.reviewEditText);
        ImageView mGoBackBtn = findViewById(R.id.cartBackBtn);
        mGoBackBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        TextView mRatingResName = findViewById(R.id.recommendLabel);
        mRatingResName.setText("Would you recommend FoodShades " + " ?");
        mRecommendBtn = findViewById(R.id.recommend);
        mNotRecommendBtn = findViewById(R.id.notrecommend);
        Button mSaveReviewBtn = findViewById(R.id.saveReviewBtn);
        mSaveReviewBtn.setOnClickListener(view -> {
            uploadReviewDetails();
        });
    }


    private void uploadReviewDetails() {
        if (TextUtils.isEmpty(mReviewEditText.getText()) || /*ratingBar.getSelectedSmiley().getRating() == -1 ||*/ (!mRecommendBtn.isChecked() && !mNotRecommendBtn.isChecked())){
            Toast.makeText(this, "Please fill the review properly", Toast.LENGTH_SHORT).show();
        }else {
            if(mRecommendBtn.isChecked()){
                recommendText = "YES";
            }else if(mNotRecommendBtn.isChecked()){
                recommendText = "NO";
            }
//            int rating = ratingBar.getSelectedSmiley().getRating();
            String review = mReviewEditText.getText().toString();

            HashMap<String, String> uploadReviewMap = new HashMap<>();
          //  uploadReviewMap.put("user_name",userName);
          //  uploadReviewMap.put("user_image", userImage);
          //  uploadReviewMap.put("uid", uid);
//            uploadReviewMap.put("rating", String.valueOf(rating));
            uploadReviewMap.put("recommended", recommendText);
            uploadReviewMap.put("review", review);

            db.collection("UserReview").add(uploadReviewMap).addOnCompleteListener(task -> {
            });
            db.collection("UserReview").get().addOnCompleteListener(task -> {
            });

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Review Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }

}