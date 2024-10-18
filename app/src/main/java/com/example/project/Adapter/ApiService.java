package com.example.project.Adapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("food/menuItems/search")
    Call<JsonObject> getFoodItems(@Query("apiKey") String apiKey, @Query("query") String query,
                                  @Query("number") String number);

    @GET("getAllRestaurants")
    Call<JsonArray> getAllRestaurants();

    @POST("placeOrder")
    Call<JsonObject> placeOrder(@Header("Authorization") String token, @Body Map<String, Object> map);

    @GET("getRestaurant/{restaurantId}")
    Call<JsonObject> getRestaurant(@Path("restaurantId") Long restaurantId);
}
