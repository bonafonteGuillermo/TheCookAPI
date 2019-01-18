package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import models.Recipe;
import models.RecipeDetails;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import utils.Utils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.Utils.*;

public class RecipeController extends Controller {

    @Inject
    FormFactory formFactory;

    @Transactional
    public Result createRecipe() {
        if (isContentTypeJSON(request())) return Results.notAcceptable("Not Acceptable");

        JsonNode jsonNode = request().body().asJson();
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bind(jsonNode);
        if (recipeForm.hasErrors()) {
            return Results.badRequest(recipeForm.errorsAsJson());
        }

        Recipe recipe = recipeForm.get();
        if (Recipe.findByName(recipe.getName()) != null) {
            return Results.status(CONFLICT);
        }

        RecipeDetails recipeDetails = recipe.getRecipeDetails();
        List<Ingredient> ingredientsToCreate = recipe.getIngredients();

        recipe.setIngredients(new ArrayList<>());
        bindIngredients(recipe, ingredientsToCreate);

        recipeDetails.save();
        recipe.save();

        Content content = views.xml.recipe.recipe.render(recipe);
        JsonNode json = play.libs.Json.toJson(recipe);
        return negotiateContent(json, content);
    }



    public Result retrieveRecipe(Integer recipeId) {
        Recipe recipe = Recipe.findById(recipeId.longValue());
        if (recipe == null) {
            return Results.notFound();
        }

        Content content = views.xml.recipe.recipe.render(recipe);
        JsonNode json = play.libs.Json.toJson(recipe);
        return negotiateContent(json, content);
    }

    public Result updateRecipe(Integer recipeId) {
        if (isContentTypeJSON(request())) return Results.notAcceptable("Not Acceptable");

        JsonNode jsonNode = request().body().asJson();
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bind(jsonNode);
        Recipe recipe = Recipe.findById(recipeId.longValue());
        if (recipe == null) {
            return Results.notFound();
        }

        Recipe newRecipe = recipeForm.get();
        List<Ingredient> ingredientsToUpdate = newRecipe.getIngredients();

        recipe.setName(newRecipe.getName());
        recipe.setIngredients(new ArrayList<>());
        recipe.setRecipeDetails(newRecipe.getRecipeDetails());
        bindIngredients(recipe, ingredientsToUpdate);

        recipe.update();

        Content content = views.xml.recipe.recipe.render(recipe);
        JsonNode json = play.libs.Json.toJson(recipe);
        return negotiateContent(json, content);
    }

    public Result deleteRecipe(Integer recipeId) {
        Recipe recipe = Recipe.findById(recipeId.longValue());
        if (recipe == null || !recipe.delete()) {
            return Results.notFound();
        }
        return ok();
    }

    public Result deleteAllRecipes() {
        int affectedRows = Recipe.deleteAll();
        if (affectedRows == 0) {
            return Results.notFound();
        }
        return ok();
    }

    public Result listRecipes() {
        List<Recipe> recipeList = Recipe.findAll();
        if (recipeList.isEmpty()) {
            return Results.notFound();
        }
        Content content = views.xml.recipe.recipes.render(recipeList);
        JsonNode json = play.libs.Json.toJson(recipeList);
        return negotiateContent(json, content);
    }

    public Result searchRecipes() {
        final String INGREDIENT = "ingredient";
        final String KIND = "kind";

        Result result = badRequest();
        Map<String, String[]> queryStringMap = request().queryString();

        String ingredientParam = Utils.getParam(queryStringMap, INGREDIENT);
        String kindParam = Utils.getParam(queryStringMap, KIND);

        if (ingredientParam != null) {
            result = listRecipesWithIngredient(ingredientParam);
        } else if (kindParam != null) {
            result = listRecipesWithIngredientKind(kindParam);
        }
        return result;
    }

    private Result listRecipesWithIngredient(String ingredient) {
        List<Recipe> recipeList = Recipe.findByIngredient(ingredient);
        if (recipeList.isEmpty()) {
            return Results.notFound();
        }
        Content content = views.xml.recipe.recipes.render(recipeList);
        JsonNode json = play.libs.Json.toJson(recipeList);
        return negotiateContent(json, content);
    }

    private Result listRecipesWithIngredientKind(String kind) {
        List<Recipe> recipeList = Recipe.findByIngredientKind(kind);
        if (recipeList.isEmpty()) {
            return Results.notFound();
        }
        Content content = views.xml.recipe.recipes.render(recipeList);
        JsonNode json = play.libs.Json.toJson(recipeList);
        return negotiateContent(json, content);
    }

    private void bindIngredients(Recipe recipe, List<Ingredient> ingredientsToBind) {
        for (Ingredient ingredientToUpdate : ingredientsToBind) {
            Ingredient ingredientInDB = Ingredient.findByName(ingredientToUpdate.getName());
            if (ingredientInDB != null) {
                bindIngredientKind(ingredientToUpdate);
                recipe.addIngredient(ingredientInDB);
            } else {
                bindIngredientKind(ingredientToUpdate);
                ingredientToUpdate.save();
                recipe.addIngredient(ingredientToUpdate);
            }
        }
    }
}