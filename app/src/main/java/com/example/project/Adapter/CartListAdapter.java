package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Domain.Food;
import com.example.project.Helper.ManagementCart;
import com.example.project.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<Food> foods;
    private ManagementCart managementCart;

    public CartListAdapter(ArrayList<Food> foods, Context context) {

        this.foods = foods;
        managementCart = new ManagementCart(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foods.get(position).getFoodName());
        holder.feeEachItem.setText(String.valueOf(foods.get(position).getFoodPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foods.get(position).getNumberInCart() * foods.get(position).getFoodPrice()) * 100.0) / 100.0));
        holder.num.setText(String.valueOf(foods.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foods.get(position).getImageUrl(), "drawable", holder.itemView.getContext().getPackageName());
        String url = foods.get(position).getImageUrl();
        Glide.with(holder.itemView.getContext())
                .load(url)
                .into(holder.pic);


        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberFood(foods, position);
                notifyDataSetChanged();
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.MinusNumerFood(foods, position);
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);
        }
    }
}
