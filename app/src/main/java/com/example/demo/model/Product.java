package com.example.demo.model;

public class Product {
    private String name, description, image_one_url, image_two_url, location, time_added, price;

    public Product() {
    }

    public Product(String name, String description, String image_one_url, String image_two_url, String location, String time_added, String price) {
        this.name = name;
        this.description = description;
        this.image_one_url = image_one_url;
        this.image_two_url = image_two_url;
        this.location = location;
        this.time_added = time_added;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_one_url() {
        return image_one_url;
    }

    public String getImage_two_url() {
        return image_two_url;
    }

    public String getLocation() {
        return location;
    }

    public String getTime_added() {
        return time_added;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_one_url(String image_one_url) {
        this.image_one_url = image_one_url;
    }

    public void setImage_two_url(String image_two_url) {
        this.image_two_url = image_two_url;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime_added(String time_added) {
        this.time_added = time_added;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
