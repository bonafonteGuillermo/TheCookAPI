package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Recipe;
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
import java.util.Optional;

import static utils.Utils.negotiateContent;

public class RecipeController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result createRecipe() {
        Result result;
        Optional<String> optional = request().contentType();

        if (optional.isPresent() && optional.get().equals(Http.MimeTypes.JSON)) {
            JsonNode jsonNode = request().body().asJson();
            Form<Recipe> recipeForm = formFactory.form(Recipe.class).bind(jsonNode);

            if (!recipeForm.hasErrors()) {
                Recipe recipe = recipeForm.get();
                recipe.save();
                Content content = views.xml.recipe.render(recipe);
                JsonNode json = play.libs.Json.toJson(recipe);
                result = negotiateContent(json, content);
            } else {
                result = Results.badRequest(recipeForm.errorsAsJson());
            }
        } else {
            result = Results.notAcceptable("Not Acceptable");
        }

        return result;
    }

    public Result retrieveRecipe(Integer recipeId) {
        Result result;
        Recipe recipe = Recipe.findById(recipeId.longValue());

        if (recipe != null) {
            Content content = views.xml.recipe.render(recipe);
            JsonNode json = play.libs.Json.toJson(recipe);
            result = negotiateContent(json, content);
        } else {
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

        Recipe recipe1 = new Recipe("Macarrones");
        Recipe recipe2 = new Recipe("Paella");

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);

        Content content = views.xml.recipes.render(recipeList);
        JsonNode json = play.libs.Json.toJson(recipeList);

        result = negotiateContent(json, content);

        return result;
    }
}
