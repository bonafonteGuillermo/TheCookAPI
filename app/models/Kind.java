package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import play.data.validation.Constraints.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import java.util.List;

@Entity
public class Kind extends BaseModel{

    private static final Finder<Long, Kind> find = new Finder<>(Kind.class);

    @Required
    @MaxLength(40)
    private String name;

    @Valid
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy= "kind")
    public List<Ingredient> ingredient;

    public static Kind findById(Long id) {
        return find.byId(id);
    }

    public static Kind findByName(String name) {
        ExpressionList<Kind> query = find.query().where().eq("name", name);
        Kind kind = query.findOne();
        return kind;
    }

    public static List<Kind> findAll() {
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

    public List<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }
}
