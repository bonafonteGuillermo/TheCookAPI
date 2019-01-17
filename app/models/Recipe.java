package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Ebean;
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
    @JsonManagedReference
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private RecipeDetails recipeDetails;

    public Recipe() {
        super();
    }

    public Recipe(String name) {
        this.name = name;
    }

    public static Recipe findById(Long id) {
        return find.byId(id);
    }

    public static List<Recipe> findAll() {
        return find.query().findList();
    }

    public static List<Recipe> findByIngredient(String ingredient){
        String sql = "SELECT R.id, R.name " +
                        "FROM recipe as R, ingredient AS I, recipe_ingredient as X " +
                        "WHERE X.recipe_id = R.id " +
                        "and X.ingredient_id = I.id " +
                        "and I.name LIKE \'"+ingredient+"\'";

        List<Recipe> recipeList = find.nativeSql(sql).findList();
        return recipeList;
    }

    public static List<Recipe> findByIngredientKind(String kind){
        String sql = "SELECT DISTINCT R.id, R.name " +
                        "FROM recipe as R, ingredient AS I, recipe_ingredient as X, kind as K " +
                        "WHERE X.recipe_id = R.id " +
                        "and X.ingredient_id = I.id " +
                        "and I.kind_id = K.id " +
                        "and K.name LIKE \'"+kind+"\'";

        List<Recipe> recipeList = find.nativeSql(sql).findList();
        return recipeList;
    }

    public static int deleteAll(){
        return Ebean.deleteAll(find.all());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Finder<Long, Recipe> getFind() {
        return find;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
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
        ingredient.getRecipes().add(this);
    }
}