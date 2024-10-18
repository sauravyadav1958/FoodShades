package com.example.project.Domain;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private Long id;
    private String restaurantName;
    private String imageUrl;

    public Restaurant(Long id, String imageUrl, String restaurantName) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.restaurantName = restaurantName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
