package com.example.project.Domain;

import java.io.Serializable;

public class Food implements Serializable {
    private String foodName;
    private String imageUrl;
    private String description;
    private Double foodPrice;
    private int numberInCart;

    public Food(String foodName, String imageUrl, String description, Double foodPrice) {
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.foodPrice = foodPrice;
    }

    public Food(String foodName, String imageUrl, String description, Double foodPrice, int numberInCart) {
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.foodPrice = foodPrice;
        this.numberInCart = numberInCart;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
