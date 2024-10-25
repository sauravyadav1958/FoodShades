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
import com.example.project.Adapter.FoodAdapter;
import com.example.project.Adapter.RestaurantAdapter;
import com.example.project.Domain.Food;
import com.example.project.Domain.Restaurant;
import com.example.project.R;
import com.example.project.databinding.ActivityMainBinding;
import com.example.project.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RestaurantAdapter.createDataParse {
    //  ActivityMainBinding: efficient and safer way to interact with views,
//  better alternative of findViewById() for layouts.
    ActivityMainBinding binding;
    FirebaseAuth auth;
    Button orderNow;
    private RecyclerView.Adapter restaurantAdapter, foodAdapter;
    private RecyclerView restaurantRecyclerView, foodRecyclerView;
    ArrayList<Restaurant> restaurantList;
    private int currentRestaurantPosition = 0;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retrofit restaurantRetrofit = new Retrofit.Builder()
            .baseUrl(Utils.hostname)
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
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
        loadRestaurants();
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
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurant", restaurantList.get(currentRestaurantPosition));
                intent.putExtras(bundle);
                startActivity(intent);
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

    private void loadRestaurantFoods() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        foodRecyclerView = findViewById(R.id.recyclerView2);
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        getRestaurantFoods(restaurantList, 0);

    }

    private void loadRestaurants() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        restaurantRecyclerView = findViewById(R.id.recyclerView);
        restaurantRecyclerView.setLayoutManager(linearLayoutManager);

        restaurantList = new ArrayList<>();
        ApiService apiService = restaurantRetrofit.create(ApiService.class);
        Call<JsonArray> restaurantJsonObject = apiService.getAllRestaurants();
        restaurantJsonObject.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray restaurants = response.body();
                for (JsonElement restaurant : restaurants) {
                    Long restaurantId = restaurant.getAsJsonObject().get("restaurantId").getAsLong();
                    String restaurantName = restaurant.getAsJsonObject().get("restaurantName").getAsString();
                    String imageUrl = restaurant.getAsJsonObject().get("imageUrl").getAsString();
                    restaurantList.add(new Restaurant(restaurantId, imageUrl, restaurantName));

                }
                restaurantAdapter = new RestaurantAdapter(restaurantList, MainActivity.this);
                restaurantRecyclerView.setAdapter(restaurantAdapter);
                loadRestaurantFoods();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                getOfflineRestaurants();
            }
        });

    }

    public void setFoods(ArrayList<Food> foodList) {
        foodAdapter = new FoodAdapter(foodList);
        foodRecyclerView.setAdapter(foodAdapter);
    }

    public void getRestaurantFoods(ArrayList<Restaurant> restaurantList, int position) {

        Long restaurantId = restaurantList.get(position).getId();
        ApiService apiService = restaurantRetrofit.create(ApiService.class);
        Call<JsonObject> foodJsonObject = apiService.getRestaurant(restaurantId);
        foodJsonObject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.raw().code() == 302) {
                    String restaurant = "";
                    try {
                        ArrayList<Food> foodList = new ArrayList<>();
                        restaurant = response.errorBody().string();
                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(restaurant, JsonObject.class);
                        JsonArray foodListJsonArray = jsonObject.getAsJsonArray("foodList");

                        for (JsonElement food : foodListJsonArray) {
                            String foodName = food.getAsJsonObject().get("foodName").getAsString();
                            String imageUrl = food.getAsJsonObject().get("imageUrl").getAsString();
                            String foodDescription = food.getAsJsonObject().get("description").getAsString();
                            double foodPrice = food.getAsJsonObject().get("foodPrice").getAsDouble();
                            foodList.add(new Food(foodName, imageUrl, foodDescription, foodPrice));
                        }
                        setFoods(foodList);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    ArrayList<Food> foodList = new ArrayList<>();
                    JsonArray foodListJsonArray = response.body().getAsJsonObject("foodList").getAsJsonArray();
                    for (JsonElement food : foodListJsonArray) {
                        String foodName = food.getAsJsonObject().get("foodName").getAsString();
                        String imageUrl = food.getAsJsonObject().get("imageUrl").getAsString();
                        String foodDescription = food.getAsJsonObject().get("description").getAsString();
                        double foodPrice = food.getAsJsonObject().get("foodPrice").getAsDouble();
                        foodList.add(new Food(foodName, imageUrl, foodDescription, foodPrice));
                    }
                    setFoods(foodList);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getOfflineFoodItems(restaurantId);
            }
        });
    }


    private void getOfflineRestaurants() {
        Gson gson = new Gson();
        JsonArray restaurants = gson.fromJson(Utils.restaurants, JsonArray.class);
        for (JsonElement restaurant : restaurants) {
            Long restaurantId = restaurant.getAsJsonObject().get("restaurantId").getAsLong();
            String restaurantName = restaurant.getAsJsonObject().get("restaurantName").getAsString();
            String imageUrl = restaurant.getAsJsonObject().get("imageUrl").getAsString();
            restaurantList.add(new Restaurant(restaurantId, imageUrl, restaurantName));

        }
        restaurantAdapter = new RestaurantAdapter(restaurantList, MainActivity.this);
        restaurantRecyclerView.setAdapter(restaurantAdapter);
        loadRestaurantFoods();
    }

    private void getOfflineFoodItems(Long restaurantId) {
        ArrayList<Food> foodList = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray restaurants = gson.fromJson(Utils.restaurants, JsonArray.class);
        for (JsonElement restaurant : restaurants) {
            String id = restaurant.getAsJsonObject().get("restaurantId").getAsString();
            if (id.equals(restaurantId)) {
                JsonArray foodListJsonArray = restaurant.getAsJsonObject().get("foodList").getAsJsonArray();
                for (JsonElement food : foodListJsonArray) {
                    String foodName = food.getAsJsonObject().get("foodName").getAsString();
                    String imageUrl = food.getAsJsonObject().get("imageUrl").getAsString();
                    String foodDescription = food.getAsJsonObject().get("description").getAsString();
                    double foodPrice = food.getAsJsonObject().get("foodPrice").getAsDouble();
                    foodList.add(new Food(foodName, imageUrl, foodDescription, foodPrice));
                }
                setFoods(foodList);
            }
        }

    }


    public void setCurrentRestaurantPosition(int position) {
        currentRestaurantPosition = position;
    }

    public int getCurrentRestaurantPosition() {
        return currentRestaurantPosition;
    }
}