package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.Domain.Food;
import com.example.project.Helper.ManagementCart;
import com.example.project.R;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCardBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private Food food;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        ImageView mGoBackBtn = findViewById(R.id.cartBackBtn);
        mGoBackBtn.setOnClickListener(view -> {
            this.onBackPressed();
        });
        managementCart = new ManagementCart(this);

        initView();
        getBundle();
    }

    private void initView() {
        addToCardBtn = findViewById(R.id.addToCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.foodPic);
    }

    private void getBundle() {
        food = (Food) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(food.getImageUrl(), "drawable", this.getPackageName());
        String url = food.getImageUrl();
        Glide.with(this)
                .load(url)
                .into(picFood);

        titleTxt.setText(food.getFoodName());
        feeTxt.setText("Rs." + food.getFoodPrice());
        descriptionTxt.setText(food.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setNumberInCart(numberOrder);
                managementCart.insertFood(food);
            }
        });
    }


}