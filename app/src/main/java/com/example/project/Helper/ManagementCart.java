package com.example.project.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.project.Domain.FoodDomain;

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
    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
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

    public ArrayList<FoodDomain> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    // increment the number of quantity, notify the listener
    public void plusNumberFood(ArrayList<FoodDomain> listfood, int position) {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listfood);
    }
    // decrement the number of quantity (remove if qty is 1), notify the listener
    public void MinusNumerFood(ArrayList<FoodDomain> listfood, int position) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", listfood);
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listFood = getListCard();
        double fee = 0;
        for (int i = 0; i < listFood.size(); i++) {
            fee = fee + (listFood.get(i).getFee() * listFood.get(i).getNumberInCart());
        }
        return fee;
    }

}
