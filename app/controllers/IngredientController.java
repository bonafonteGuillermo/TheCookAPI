package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import java.util.List;

import static utils.Utils.negotiateContent;

public class IngredientController extends Controller{

    public Result listIngredients(){
        Result result;

        List<Ingredient> ingredientList = Ingredient.findAll();

        if (!ingredientList.isEmpty()) {
            Content content = views.xml.ingredient.ingredients.render(ingredientList);
            JsonNode json = play.libs.Json.toJson(ingredientList);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }

        return result;
    }
}
