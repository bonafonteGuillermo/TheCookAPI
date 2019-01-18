package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import models.Kind;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

import static utils.Utils.*;

public class IngredientController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result createIngredient() {
        if (isContentTypeJSON(request())) return Results.notAcceptable("Not Acceptable");

        JsonNode jsonNode = request().body().asJson();
        Form<Ingredient> ingredientForm = formFactory.form(Ingredient.class).bind(jsonNode);
        if (ingredientForm.hasErrors()) {
            return Results.badRequest(ingredientForm.errorsAsJson());
        }

        Ingredient ingredientToCreate = ingredientForm.get();
        if(Ingredient.findByName(ingredientToCreate.getName())!=null){
            return Results.status(CONFLICT);
        }

        bindIngredientKind(ingredientToCreate);
        ingredientToCreate.save();

        Content content = views.xml.ingredient.ingredient.render(ingredientToCreate);
        JsonNode json = play.libs.Json.toJson(ingredientToCreate);
        return negotiateContent(json, content);
    }

    public Result retrieveIngredient(Integer ingredientId) {
        Ingredient ingredient = Ingredient.findById(ingredientId.longValue());
        if (ingredient == null) {
            return Results.notFound();
        }
        Content content = views.xml.ingredient.ingredient.render(ingredient);
        JsonNode json = play.libs.Json.toJson(ingredient);
        return negotiateContent(json, content);
    }

    public Result updateIngredient(Integer ingredientId) {
        if (isContentTypeJSON(request())) return Results.notAcceptable("Not Acceptable");

        JsonNode jsonNode = request().body().asJson();
        Form<Ingredient> ingredientForm = formFactory.form(Ingredient.class).bind(jsonNode);
        if (ingredientForm.hasErrors()) {
            return Results.badRequest(ingredientForm.errorsAsJson());
        }

        Ingredient ingredientInDB = Ingredient.findById(ingredientId.longValue());
        if (ingredientInDB == null) {
            return Results.notFound();
        }

        Ingredient newIngredient = ingredientForm.get();
        ingredientInDB.setName(newIngredient.getName());
        ingredientInDB.setKind(new Kind());
        bindIngredientKind(newIngredient);
        ingredientInDB.setKind(newIngredient.getKind());

        ingredientInDB.update();

        Content content = views.xml.ingredient.ingredient.render(ingredientInDB);
        JsonNode json = play.libs.Json.toJson(ingredientInDB);
        return negotiateContent(json, content);
    }

    public Result deleteIngredient(Integer ingredientid) {
        Ingredient ingredient = Ingredient.findById(ingredientid.longValue());
        if (ingredient == null || !ingredient.delete()) {
            return Results.notFound();
        }
        return ok();
    }

    public Result deleteAllIngredients() {
        int affectedRows = Ingredient.deleteAll();
        if (affectedRows == 0) {
            return Results.notFound();
        }
        return ok();
    }

    public Result listIngredients() {
        List<Ingredient> ingredientList = Ingredient.findAll();
        if (ingredientList.isEmpty()) {
            return  Results.notFound();
        }
        Content content = views.xml.ingredient.ingredients.render(ingredientList);
        JsonNode json = play.libs.Json.toJson(ingredientList);
        return negotiateContent(json, content);
    }
}