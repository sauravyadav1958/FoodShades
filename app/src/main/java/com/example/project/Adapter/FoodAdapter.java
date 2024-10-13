package com.example.project.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Activity.ShowDetailActivity;
import com.example.project.Domain.Food;
import com.example.project.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    ArrayList<Food> foodList;

    public FoodAdapter(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foodList.get(position).getFoodName());
        holder.fee.setText(String.valueOf(foodList.get(position).getFoodPrice()));
        String url = foodList.get(position).getImageUrl();
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodList.get(position).getImageUrl(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(url)
                .into(holder.pic);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", foodList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic;
        TextView addBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
