package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Type extends BaseModel{

    @EmbeddedId
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy= "type")
    @JsonIgnore
    public ArrayList<Ingredient> ingredients;

    public Type() {
    }

    public Type(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
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
}
