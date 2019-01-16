package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Kind;
import models.Recipe;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import java.util.List;

import static utils.Utils.negotiateContent;

public class KindController extends Controller {

    public Result listKinds(){
        Result result;

        List<Kind> kindList = Kind.findAll();

        if (!kindList.isEmpty()) {
            Content content = views.xml.kind.kinds.render(kindList);
            JsonNode json = play.libs.Json.toJson(kindList);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }

        return result;
    }

    public Result deleteKind(Integer kindId) {
        Result result;
        Kind kind = Kind.findById(kindId.longValue());

        if (kind != null && kind.delete()) {
            result = ok();
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result deleteAllKinds() {
        Result result;
        int affectedRows = Kind.deleteAll();

        if (affectedRows != 0) {
            result = ok();
        } else {
            result = Results.notFound();
        }
        return result;
    }
}
