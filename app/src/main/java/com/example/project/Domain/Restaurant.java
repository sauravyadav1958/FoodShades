package com.example.project.Domain;

public class Restaurant {
    private String id;
    private String restaurantName;
    private String imageUrl;

    public Restaurant(String id, String imageUrl, String restaurantName) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.restaurantName = restaurantName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
