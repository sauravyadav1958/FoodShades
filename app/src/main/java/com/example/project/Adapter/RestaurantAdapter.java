package com.example.project.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Domain.Food;
import com.example.project.Domain.Restaurant;
import com.example.project.R;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    ArrayList<Restaurant> restaurantList;
    private createDataParse createDataParse;
    Context context;

    public RestaurantAdapter(ArrayList<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
        createDataParse = (createDataParse) this.context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.restaurantName.setText(restaurantList.get(position).getRestaurantName());

        // ContextCompat: provides backwards-compatible versions of methods available in the Context class.
        // older Android version compatibility support.
        // holder.itemView.getContext(): For accessing the context for specific item
        // within a RecyclerView adapter or view holder. (It puts a scope limit)
//        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background1));


        // For getting ID from image name.
//        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Uri uri = Uri.parse(restaurantList.get(position).getImageUrl());
        Glide.with(holder.itemView.getContext())
                .load(uri)
                .into(holder.restaurantPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentCategoryPosition = createDataParse.getCurrentRestaurantPosition();
                if (currentCategoryPosition == position) {
                    return;
                }
                createDataParse.getRestaurantFoods(restaurantList, position);
                createDataParse.setCurrentRestaurantPosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        ImageView restaurantPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.categoryName);
            restaurantPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

    }

//    public boolean isImageUrlLoadable(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////            connection.setRequestMethod("HEAD"); // Use HEAD to only fetch headers
//            connection.setConnectTimeout(5000);  // Set timeout for the connection
//            connection.setReadTimeout(5000);
//            connection.connect();
//
//            int responseCode = connection.getResponseCode();
//            // Check if response code is 200 OK
//            return responseCode == HttpURLConnection.HTTP_OK;
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle exceptions like MalformedURLException, IOException
//            return false;
//        }
//    }

    public interface createDataParse {
        public void setFoods(ArrayList<Food> foodList);

        public void getRestaurantFoods(ArrayList<Restaurant> foodCategories, int position);

        public void setCurrentRestaurantPosition(int position);

        public int getCurrentRestaurantPosition();
    }
}
