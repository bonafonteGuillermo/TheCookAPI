package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import models.Kind;
import models.Recipe;
import models.RecipeDetails;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
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

    @Transactional
    public Result createRecipe() {
        Result result;
        Optional<String> optional = request().contentType();

        if (optional.isPresent() && optional.get().equals(Http.MimeTypes.JSON)) {
            JsonNode jsonNode = request().body().asJson();
            Form<Recipe> recipeForm = formFactory.form(Recipe.class).bind(jsonNode);

            if (!recipeForm.hasErrors()) {
                Recipe recipe = recipeForm.get();
                RecipeDetails recipeDetails = recipe.getRecipeDetails();
                List<Ingredient> ingredientsToCreate = recipe.getIngredients();

                recipe.setIngredients(new ArrayList<>());

                for (Ingredient ingredientToCreate : ingredientsToCreate) {
                    Ingredient ingredientInDB = Ingredient.findByName(ingredientToCreate.getName());
                    if (ingredientInDB != null) {
                        recipe.addIngredient(ingredientInDB);
                    } else {
                        Kind typeToCreate = ingredientToCreate.getKind();
                        Kind typeInDB = Kind.findByName(typeToCreate.getName());
                        if(typeInDB != null){
                            ingredientToCreate.setKind(typeInDB);
                        }else{
                            typeToCreate.save();
                            ingredientToCreate.setKind(typeToCreate);
                        }
                        ingredientToCreate.save();
                        recipe.addIngredient(ingredientToCreate);
                    }
                }
                recipeDetails.save();
                recipe.save();

                Content content = views.xml.recipe.recipe.render(recipe);
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
            Content content = views.xml.recipe.recipe.render(recipe);
            JsonNode json = play.libs.Json.toJson(recipe);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result updateRecipe(Integer recipeId, String newName) {
        Result result;
        Recipe recipe = Recipe.findById(recipeId.longValue());

        if (recipe != null) {
            recipe.setName(newName);
            recipe.update();

            Content content = views.xml.recipe.recipe.render(recipe);
            JsonNode json = play.libs.Json.toJson(recipe);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result deleteRecipe(Integer recipeId) {
        Result result;
        Recipe recipe = Recipe.findById(recipeId.longValue());

        if (recipe != null && recipe.delete()) {
            result = ok();
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result deleteAllRecipes() {
        Result result;
        int affectedRows = Recipe.deleteAll();

        if (affectedRows != 0) {
            result = ok();
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result listRecipes() {
        Result result;

        List<Recipe> recipeList = Recipe.findAll();

        if (!recipeList.isEmpty()) {
            Content content = views.xml.recipe.recipes.render(recipeList);
            JsonNode json = play.libs.Json.toJson(recipeList);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }

        return result;
    }

    public Result listRecipesWithIngredient(String ingredient){
        Result result;

        List<Recipe> recipeList = Recipe.findByIngredient(ingredient);

        if (!recipeList.isEmpty()) {
            Content content = views.xml.recipe.recipes.render(recipeList);
            JsonNode json = play.libs.Json.toJson(recipeList);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }

        return result;
    }

    public Result listRecipesWithIngredientKind(String kind){
        Result result;

        List<Recipe> recipeList = Recipe.findByIngredientKind(kind);

        if (!recipeList.isEmpty()) {
            Content content = views.xml.recipe.recipes.render(recipeList);
            JsonNode json = play.libs.Json.toJson(recipeList);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }

        return result;
    }
}
