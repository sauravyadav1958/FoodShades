package com.example.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.ApiService;
import com.example.project.Adapter.CategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.Category;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.example.project.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.createDataParse {
    //  ActivityMainBinding: efficient and safer way to interact with views,
//  better alternative of findViewById() for layouts.
    ActivityMainBinding binding;
    FirebaseAuth auth;
    Button orderNow;
    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView categoryRecyclerView, popularRecyclerView;
    ArrayList<Category> categoryList;
    private int currentCategoryPosition = 0;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        orderNow = findViewById(R.id.ordernow);
        //  logout = findViewById(R.id.logoutbtn);

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        loadCategories();
        loadPopularFoods();
        bottomNavigation();


    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout profileBtn = findViewById(R.id.profilebtn);
        LinearLayout settingBtn = findViewById(R.id.setting);
        LinearLayout supportBtn = findViewById(R.id.support);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SupportActivity.class));
            }
        });

    }

    private void loadPopularFoods() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView = findViewById(R.id.recyclerView2);
        popularRecyclerView.setLayoutManager(linearLayoutManager);
        getCategoryFoods(categoryList, 0);

    }

    private void loadCategories() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView = findViewById(R.id.recyclerView);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        categoryList = new ArrayList<>();
        categoryList.add(new Category("Pizza", "cat_1"));
        categoryList.add(new Category("Burger", "cat_2"));
        categoryList.add(new Category("Hot dog", "cat_3"));
        categoryList.add(new Category("Drink", "cat_4"));
        categoryList.add(new Category("Donut", "cat_5"));

        categoryAdapter = new CategoryAdapter(categoryList, MainActivity.this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    public void setPopular(ArrayList<FoodDomain> foodList) {
        popularAdapter = new PopularAdapter(foodList);
        popularRecyclerView.setAdapter(popularAdapter);
    }

    public void getCategoryFoods(ArrayList<Category> foodCategories, int position) {
        String category = foodCategories.get(position).getTitle();
//        String url = "https://api.spoonacular.com/food/menuItems/search?apiKey=96cc2859945c4256aee0b9dbd3865603&query=" + category + "&number=5";
        ApiService apiService = retrofit.create(ApiService.class);
        String limit = "5";
        Call<JsonObject> foodJsonObject = apiService.getFoodItems("96cc2859945c4256aee0b9dbd3865603", category, limit);
        foodJsonObject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ArrayList<FoodDomain> foodList = new ArrayList<>();
                JsonArray foodItems = response.body().getAsJsonArray("menuItems");
                for (JsonElement track : foodItems) {
                    String title = track.getAsJsonObject().get("title").getAsString();
                    String pic = track.getAsJsonObject().get("image").getAsString();
                    foodList.add(new FoodDomain(title, pic, "random long description for current food, tasty and healthy as well", 199.00));
                }
                setPopular(foodList);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void setCurrentCategoryPosition(int position) {
        currentCategoryPosition = position;
    }

    public int getCurrentCategoryPosition() {
        return currentCategoryPosition;
    }
}