package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Ingredient extends BaseModel{

    @Constraints.Required
    private String name;

    @Constraints.Required
	@ManyToOne
	private Type type;

    public Ingredient() {
    }

    public Ingredient(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}