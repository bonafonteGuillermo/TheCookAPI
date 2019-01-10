package models;

import java.util.ArrayList;

public class Recipe {
    private Integer id;
    private String name;
    private String imageURL;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Recipe() {
    }

    public Recipe(Integer id, String name, String imageURL, ArrayList<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}