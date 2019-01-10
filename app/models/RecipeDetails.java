package models;

import javax.persistence.Entity;

@Entity
public class RecipeDetails extends BaseModel{
	private String imageURL;
	private String description;

	public RecipeDetails() {
	}

	public RecipeDetails(String imageURL, String description) {
		this.imageURL = imageURL;
		this.description = description;
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