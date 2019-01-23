package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints.*;
import validators.DescriptionWithTwoWordsAtLeast;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RecipeDetails extends Model {

	private static final Finder<Long,RecipeDetails> find = new Finder<>(RecipeDetails.class);

	@Id
    @JsonIgnore
	private Long id;

	@URL
	@NotBlank
	@MaxLength(255)
	private String imageURL;

	@DescriptionWithTwoWordsAtLeast
	private String description;

	public static RecipeDetails findById(Long id) {
		return find.byId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}