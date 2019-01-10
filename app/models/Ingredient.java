package models;

import javax.persistence.Entity;

@Entity
public class Ingredient extends BaseModel{
	private String name;
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