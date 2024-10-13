package com.example.project.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.project.Domain.Food;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    //    lightweight, simple key-value storage system for Android
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    // If item alreadyExist just update the quantity else insert the newItem
    public void insertFood(Food item) {
        ArrayList<Food> listFood = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getFoodName().equals(item.getFoodName())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", listFood);
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<Food> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    // increment the number of quantity, notify the listener
    public void plusNumberFood(ArrayList<Food> listfood, int position) {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listfood);
    }
    // decrement the number of quantity (remove if qty is 1), notify the listener
    public void MinusNumerFood(ArrayList<Food> listfood, int position) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", listfood);
    }

    public Double getTotalFee() {
        ArrayList<Food> listFood = getListCard();
        double fee = 0;
        for (int i = 0; i < listFood.size(); i++) {
            fee = fee + (listFood.get(i).getFoodPrice() * listFood.get(i).getNumberInCart());
        }
        return fee;
    }

}
