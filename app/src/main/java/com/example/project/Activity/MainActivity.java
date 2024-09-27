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
import com.example.project.Domain.CategoryDomain;
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
    ActivityMainBinding binding;
    FirebaseAuth auth;
    Button ordernow;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    ArrayList<CategoryDomain> categoryList;
    private int currentCategoryPosition = 0;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //*MADE WITH LOVE BY ADESH

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        ordernow = findViewById(R.id.ordernow);
        //  logout = findViewById(R.id.logoutbtn);

        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();


    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profilebtn = findViewById(R.id.profilebtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
       /* profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyProfileActivity.class));
            }
        });*/
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        getCategoryFoods(categoryList, 0);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("Pizza", "cat_1"));
        categoryList.add(new CategoryDomain("Burger", "cat_2"));
        categoryList.add(new CategoryDomain("Hot dog", "cat_3"));
        categoryList.add(new CategoryDomain("Drink", "cat_4"));
        categoryList.add(new CategoryDomain("Donut", "cat_5"));

        adapter = new CategoryAdapter(categoryList, MainActivity.this);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    public void profile(View view) {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }

    public void support(View view) {
        startActivity(new Intent(MainActivity.this, SupportActivity.class));
    }

    public void setting(View view) {
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
    }

    public void setFoodItems(ArrayList<FoodDomain> foodList) {
        adapter2 = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    public void getCategoryFoods(ArrayList<CategoryDomain> categoryDomains, int position) {
        String category = categoryDomains.get(position).getTitle();
        String url = "https://api.spoonacular.com/food/menuItems/search?apiKey=96cc2859945c4256aee0b9dbd3865603&query=" + category + "&number=5";
        ApiService apiService = retrofit.create(ApiService.class);
        String limit = "5";
        Call<JsonObject> foodJsonObject = apiService.getFoodItems("96cc2859945c4256aee0b9dbd3865603", category, limit);
        foodJsonObject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ArrayList<FoodDomain> foodlist = new ArrayList<>();
                JsonArray foodItems = response.body().getAsJsonArray("menuItems");
                for (JsonElement track : foodItems) {
                    String title = track.getAsJsonObject().get("title").getAsString();
                    String pic = track.getAsJsonObject().get("image").getAsString();
//                            boolean loadable = isImageUrlLoadable(pic);
//                            if (!loadable) {
//                                continue;
//                            }
                    foodlist.add(new FoodDomain(title, pic, "random long description for current food, tasty and healthy as well", 199.00));
                }
                setFoodItems(foodlist);

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