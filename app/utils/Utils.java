package utils;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import models.Kind;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import java.util.Map;

public abstract class Utils extends Controller {

    public static Result negotiateContent(JsonNode json, Content content) {
        Result result;

        if (request().accepts(Http.MimeTypes.JSON)) {
            result = Results.ok(json)
                    .as(Http.MimeTypes.JSON);
        } else if (request().accepts(Http.MimeTypes.XML)) {
            result = Results.ok(content)
                    .as(Http.MimeTypes.XML);
        } else {
            result = Results.notAcceptable("Not Acceptable");
        }
        return result;
    }

    public static String getParam(Map<String, String[]> queryStringMap, String queryParam){
        if (queryStringMap.containsKey(queryParam)) {
            String[] param = queryStringMap.get(queryParam);
            if (param[0] != null && !param[0].isEmpty()) {
                return param[0];
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public static void bindIngredientKind(Ingredient ingredientToUpdate) {
        Kind kindToUpdate = ingredientToUpdate.getKind();
        Kind kindInDB = Kind.findByName(kindToUpdate.getName());
        if (kindInDB != null) {
            ingredientToUpdate.setKind(kindInDB);
        } else {
            kindToUpdate.save();
            ingredientToUpdate.setKind(kindToUpdate);
        }
    }
}
