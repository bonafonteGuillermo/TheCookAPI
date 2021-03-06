package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import play.data.validation.Constraints.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient extends BaseModel{

    private static final Finder<Long,Ingredient> find = new Finder<>(Ingredient.class);

    @Required
    @MaxLength(40)
    private String name;

    @Valid
    @JsonBackReference
    @ManyToMany (mappedBy = "ingredients")
    private List<Recipe> recipes = new ArrayList<>();

    @Valid
	@ManyToOne
    @JsonManagedReference
	private Kind kind;

    public static Ingredient findById(Long id) {
        return find.byId(id);
    }

    public static Ingredient findByName(String name) {
        ExpressionList<Ingredient> query = find.query().where().eq("name", name);
        Ingredient ingredient = query.findOne();
        return ingredient;
    }

    public static List<Ingredient> findAll() {
        return find.query().findList();
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

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
        kind.getIngredient().add(this);
    }
}