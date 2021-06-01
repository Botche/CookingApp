package com.example.cookingapp.Models;

public class Recipe {
    private String id;
    private String name;
    private Number grams;
    private String userId;

    public Recipe(String id, String name, Number grams, String userId) {
        this.id = id;
        this.name = name;
        this.grams = grams;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getGrams() {
        return grams;
    }

    public void setGrams(Number grams) {
        this.grams = grams;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}