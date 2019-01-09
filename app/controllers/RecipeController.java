package controllers;

import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;

public class RecipeController extends Controller {

    public Result createRecipe(String name) { return ok("Created "+name); }

    public Result retrieveRecipe(Integer recipeId) {
        Result result;
        if(request().accepts(Http.MimeTypes.JSON)){
            result = ok("Retrieve JSON"+recipeId)
                    .as(Http.MimeTypes.JSON);
        }else if(request().accepts(Http.MimeTypes.XML)){
            result = ok("Retrieve XML"+recipeId)
                    .as(Http.MimeTypes.XML);
        }else{
            result = notAcceptable("Not Acceptable");
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
        return ok("List all recipes");
    }
}
