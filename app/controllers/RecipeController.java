package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import models.Recipe;
import play.mvc.*;
import play.twirl.api.Content;
import java.util.ArrayList;
import java.util.List;

public class RecipeController extends Controller {

    public Result createRecipe(String name) { return ok("Created "+name); }

    public Result retrieveRecipe(Integer recipeId) {
        Result result;

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Macarrones");
        recipe.setImageURL("imagen");
        recipe.setIngredients(new ArrayList<Ingredient>());

        if(request().accepts(Http.MimeTypes.JSON)){
            JsonNode json = play.libs.Json.toJson(recipe);
            result = Results.ok(json)
                    .as(Http.MimeTypes.JSON);

        }else if(request().accepts(Http.MimeTypes.XML)){
            Content content = views.xml.recipe.render(recipe);
            result = Results.ok(content)
                    .as(Http.MimeTypes.XML);

        }else{
            result = Results.notAcceptable("Not Acceptable");
        }

        return result;
    }

    public Result updateRecipe(Integer id, String newName) {
        return ok("Updated "+id+"/"+newName);
    }

    public Result deleteRecipe(Integer recipeId) {
        return ok("Deleted "+recipeId);
    }

    public Result listRecipes() {
        Result result;

        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setName("Macarrones");
        recipe1.setImageURL("imagen");
        recipe1.setIngredients(new ArrayList<>());

        Recipe recipe2 = new Recipe();
        recipe2.setId(2);
        recipe2.setName("Paella");
        recipe2.setImageURL("imagen");
        recipe2.setIngredients(new ArrayList<>());

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);

        if(request().accepts(Http.MimeTypes.JSON)){
            JsonNode json = play.libs.Json.toJson(recipeList);
            result = Results.ok(json)
                    .as(Http.MimeTypes.JSON);

        }else if(request().accepts(Http.MimeTypes.XML)){
            Content content = views.xml.recipes.render(recipeList);
            result = Results.ok(content)
                    .as(Http.MimeTypes.XML);

        }else{
            result = Results.notAcceptable("Not Acceptable");
        }

        return result;
    }
}
