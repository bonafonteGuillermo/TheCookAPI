package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient extends BaseModel{

    public static final Finder<Long,Ingredient> find = new Finder<>(Ingredient.class);

    private String name;

    @ManyToMany (mappedBy = "ingredients")
    @JsonBackReference
    private List<Recipe> recipes = new ArrayList<>();

    /*
	@ManyToOne
	@JsonBackReference
	private Type type;*/

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

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /*public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }*/
}