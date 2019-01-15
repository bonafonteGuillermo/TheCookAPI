package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;

import javax.persistence.*;

@Entity
public class Type extends BaseModel{

    public static final Finder<Long,Type> find = new Finder<>(Type.class);

    @EmbeddedId
    private String name;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy= "type")
    @JsonBackReference
    public Ingredient ingredient;*/

    public static Type findById(Long id) {
        return find.byId(id);
    }

    public static Type findByName(String name) {
        ExpressionList<Type> query = find.query().where().eq("name", name);
        Type type = query.findOne();
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }*/
}
