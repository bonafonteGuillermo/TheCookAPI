package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Ingredient extends BaseModel{

    public static final Finder<Long,Ingredient> find = new Finder<>(Ingredient.class);

    @Constraints.Required
    @Column(unique = true)
    private String name;

    /*@Constraints.Required
	@ManyToOne
	private Type type;*/

    @ManyToMany (mappedBy = "ingredients")
    @JsonBackReference
    private ArrayList<Recipe> recipes = new ArrayList<>();

    public Ingredient(@Constraints.Required String name, ArrayList<Recipe> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    /* public Ingredient(@Constraints.Required String name, @Constraints.Required Type type, ArrayList<Recipe> recipes) {
        this.name = name;
        this.type = type;
        this.recipes = recipes;
    }*/

    public static Ingredient findById(Long id) {
        return find.byId(id);
    }

    public static Ingredient findByName(String name) {
        ExpressionList<Ingredient> query = find.query().where().eq("name", name);
        Ingredient ingredient = query.findOne();
        return ingredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }*/

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}