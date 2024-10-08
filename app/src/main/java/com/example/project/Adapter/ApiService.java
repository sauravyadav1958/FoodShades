package com.example.project.Adapter;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("food/menuItems/search")
    Call<JsonObject> getFoodItems(@Query("apiKey") String apiKey, @Query("query") String query,
                                  @Query("number") String number);

}
