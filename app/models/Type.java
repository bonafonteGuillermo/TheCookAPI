package models;

import javax.persistence.Entity;

@Entity
public class Type extends BaseModel{
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
