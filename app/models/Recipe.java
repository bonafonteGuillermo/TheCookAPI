package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends BaseModel {

    public static final Finder<Long,Recipe> find = new Finder<>(Recipe.class);

    @Constraints.Required
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private RecipeDetails recipeDetails;

    public Recipe() {
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, RecipeDetails recipeDetails) {
        this.name = name;
        this.ingredients = ingredients;
        this.recipeDetails = recipeDetails;
    }

    public static Recipe findById(Long id) {
        return find.byId(id);
    }

    public static List<Recipe> findAll() {
        return find.query().findList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeDetails getRecipeDetails() {
        return recipeDetails;
    }

    public void setRecipeDetails(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

}