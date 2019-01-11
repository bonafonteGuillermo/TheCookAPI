package utils;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

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
}
