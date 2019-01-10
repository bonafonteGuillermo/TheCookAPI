package models;

import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Recipe extends Model{

    @Id
    private Long id;

    @Constraints.Required
    private String name;

    private String imageURL;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Recipe() {
    }

    public Recipe(String name, String imageURL, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.imageURL = imageURL;
        this.ingredients = ingredients;
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