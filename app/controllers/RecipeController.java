package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Recipe;
import models.RecipeDetails;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RecipeController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result createRecipe() {

        Result result;

        JsonNode jsonNode = request().body().asJson();
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bind(jsonNode);
        if (!recipeForm.hasErrors()){

            Recipe recipe = recipeForm.get();
            recipe.save();

            if (request().accepts(Http.MimeTypes.JSON)) {
                JsonNode json = play.libs.Json.toJson(recipe);
                result = Results.ok(json)
                        .as(Http.MimeTypes.JSON);

            } else if (request().accepts(Http.MimeTypes.XML)) {
                Content content = views.xml.recipe.render(recipe);
                result = Results.ok(content)
                        .as(Http.MimeTypes.XML);

            } else {
                result = Results.notAcceptable("Not Acceptable");
            }

        }else{
            result = Results.badRequest(recipeForm.errorsAsJson());
        }

        return result;
    }

    public Result retrieveRecipe(Integer recipeId) {
        Result result;

        Recipe recipe = Recipe.findById(recipeId.longValue());

        if(recipe != null) {
            if (request().accepts(Http.MimeTypes.JSON)) {
                JsonNode json = play.libs.Json.toJson(recipe);
                result = Results.ok(json)
                        .as(Http.MimeTypes.JSON);

            } else if (request().accepts(Http.MimeTypes.XML)) {
                Content content = views.xml.recipe.render(recipe);
                result = Results.ok(content)
                        .as(Http.MimeTypes.XML);

            } else {
                result = Results.notAcceptable("Not Acceptable");
            }
        }else{
            result = Results.notFound();
        }

        return result;
    }

    public Result updateRecipe(Integer id, String newName) {
        return ok("Updated " + id + "/" + newName);
    }

    public Result deleteRecipe(Integer recipeId) {
        return ok("Deleted " + recipeId);
    }

    public Result listRecipes() {

        Result result;

/*
        Recipe recipe1 = new Recipe("Macarrones", new ArrayList<>(), new RecipeDetails());
        Recipe recipe2 = new Recipe("Paella", new ArrayList<>(), new RecipeDetails());
*/
        Recipe recipe1 = new Recipe("Macarrones");
        Recipe recipe2 = new Recipe("Paella");

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);

        if (request().accepts(Http.MimeTypes.JSON)) {
            JsonNode json = play.libs.Json.toJson(recipeList);
            result = Results.ok(json)
                    .as(Http.MimeTypes.JSON);

        } else if (request().accepts(Http.MimeTypes.XML)) {
            Content content = views.xml.recipes.render(recipeList);
            result = Results.ok(content)
                    .as(Http.MimeTypes.XML);

        } else {
            result = Results.notAcceptable("Not Acceptable");
        }

        return result;
    }
}
