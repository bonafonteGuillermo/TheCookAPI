package models;

import java.util.ArrayList;

public class Recipe{
	private Integer id;
	private String name;
	private String imageURL;
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
}