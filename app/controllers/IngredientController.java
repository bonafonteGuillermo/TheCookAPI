package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static utils.Utils.bindIngredientKind;
import static utils.Utils.negotiateContent;

public class IngredientController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result createIngredient(){
        Result result;
        Optional<String> optional = request().contentType();

        if (optional.isPresent() && optional.get().equals(Http.MimeTypes.JSON)) {
            JsonNode jsonNode = request().body().asJson();
            Form<Ingredient> ingredientForm = formFactory.form(Ingredient.class).bind(jsonNode);

            if (!ingredientForm.hasErrors()) {
                Ingredient ingredientToCreate = ingredientForm.get();
                bindIngredientKind(ingredientToCreate);

                ingredientToCreate.save();

                Content content = views.xml.ingredient.ingredient.render(ingredientToCreate);
                JsonNode json = play.libs.Json.toJson(ingredientToCreate);
                result = negotiateContent(json, content);
            } else {
                result = Results.badRequest(ingredientForm.errorsAsJson());
            }
        } else {
            result = Results.notAcceptable("Not Acceptable");
        }
        return result;
    }

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

    public Result deleteIngredient(Integer ingredientid){
        Result result;
        Ingredient ingredient = Ingredient.findById(ingredientid.longValue());

        if (ingredient != null && ingredient.delete()) {
            result = ok();
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result deleteAllIngredients() {
        Result result;
        int affectedRows = Ingredient.deleteAll();

        if (affectedRows != 0) {
            result = ok();
        } else {
            result = Results.notFound();
        }
        return result;
    }

}