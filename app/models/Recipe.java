package models;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Recipe extends BaseModel {

    public static final Finder<Long,Recipe> find = new Finder<>(Recipe.class);

    @Constraints.Required
    private String name;
    /*private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private RecipeDetails details;*/

    public Recipe() {
    }

    public Recipe(String name) {
        this.name = name;
    }

    /*public Recipe(String name, ArrayList<Ingredient> ingredients, RecipeDetails details) {
        this.name = name;
        this.ingredients = ingredients;
        this.details = details;
    }*/

    public static Recipe findById(Long id) {
        return find.byId(id);
    }

    public static Recipe findByName(String name) {
        ExpressionList<Recipe> query = find.query().where().eq("name", name);
        Recipe recipe = query.findOne();

        return recipe;
    }

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