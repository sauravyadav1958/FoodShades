package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.CartListAdapter;
import com.example.project.Helper.ManagementCart;
import com.example.project.Interface.ChangeNumberItemsListener;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private Button checkout;
    private String mTotalAmount;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        checkout = findViewById(R.id.checkoutbtn);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), ChangeLocationActivity.class);
                    intent.putExtra("total", totalTxt.getText().toString());
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                }
            }
        });

        managementCart = new ManagementCart(this);

        initView();
        initList();
        calculateCard();
        bottomNavigation();
    }

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profilebtn);
        LinearLayout settingBtn = findViewById(R.id.setting);
        LinearLayout supportBtn = findViewById(R.id.support);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, ProfileActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, SettingActivity.class));
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, SupportActivity.class));
            }
        });


    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        // TODO understand CartListAdapter more after ManagementCart
        adapter = new CartListAdapter(managementCart.getListCard(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    // TODO understand calculateCard more after ManagementCart
    private void calculateCard() {
        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;

        totalFeeTxt.setText("Rs." + itemTotal);
        taxTxt.setText("Rs." + tax);
        deliveryTxt.setText("Rs." + delivery);
        totalTxt.setText("Rs." + total);


    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        recyclerViewList = findViewById(R.id.recyclerview);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyCart);
        scrollView = findViewById(R.id.scrollView4);

    }


}