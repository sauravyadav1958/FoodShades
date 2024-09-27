package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoryDomains;
    private createDataParse createDataParse;
    Context context;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains, Context context) {
        this.categoryDomains = categoryDomains;
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
        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        String picUrl = "";
        switch (position) {
            case 0: {
                picUrl = "cat_1";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background1));
                break;
            }
            case 1: {
                picUrl = "cat_2";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background2));
                break;
            }
            case 2: {
                picUrl = "cat_3";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background3));
                break;
            }
            case 3: {
                picUrl = "cat_4";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background4));
                break;
            }
            case 4: {
                picUrl = "cat_5";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background5));
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentCategoryPosition = createDataParse.getCurrentCategoryPosition();
                if (currentCategoryPosition == position) {
                    return;
                }
                createDataParse.getCategoryFoods(categoryDomains, position);
                createDataParse.setCurrentCategoryPosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
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
        public void setFoodItems(ArrayList<FoodDomain> foodList);

        public void getCategoryFoods(ArrayList<CategoryDomain> categoryDomains, int position);

        public void setCurrentCategoryPosition(int position);

        public int getCurrentCategoryPosition();
    }
}
