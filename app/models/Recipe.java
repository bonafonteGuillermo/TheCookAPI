package models;

import play.data.validation.Constraints;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Recipe extends BaseModel {

    @Constraints.Required
    //TODO ***@EmbeddedId** with this anotation we can use primary composite key if needed
    private String name;
    /*private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private RecipeDetails details;*/

    public Recipe() {
    }

    public Recipe(@Constraints.Required String name) {
        this.name = name;
    }

    /*public Recipe(String name, ArrayList<Ingredient> ingredients, RecipeDetails details) {
        this.name = name;
        this.ingredients = ingredients;
        this.details = details;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeDetails getDetails() {
        return details;
    }

    public void setDetails(RecipeDetails details) {
        this.details = details;
    }*/
}