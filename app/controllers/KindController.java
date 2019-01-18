package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Kind;
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

import static utils.Utils.negotiateContent;

public class KindController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result createKind() {
        Result result;
        Optional<String> optional = request().contentType();

        if (optional.isPresent() && optional.get().equals(Http.MimeTypes.JSON)) {
            JsonNode jsonNode = request().body().asJson();
            Form<Kind> kindForm = formFactory.form(Kind.class).bind(jsonNode);

            if (!kindForm.hasErrors()) {
                Kind kindToCreate = kindForm.get();
                Kind kindInDB = Kind.findByName(kindToCreate.getName());
                if (kindInDB == null) {
                    kindToCreate.save();

                    Content content = views.xml.kind.kind.render(kindToCreate);
                    JsonNode json = play.libs.Json.toJson(kindToCreate);
                    result = negotiateContent(json, content);
                }else{
                    result = Results.status(CONFLICT);
                }
            } else {
                result = Results.badRequest(kindForm.errorsAsJson());
            }
        } else {
            result = Results.notAcceptable("Not Acceptable");
        }
        return result;
    }

    public Result retrieveKind(Integer kindId) {
        Result result;
        Kind kind = Kind.findById(kindId.longValue());

        if (kind != null) {
            Content content = views.xml.kind.kind.render(kind);
            JsonNode json = play.libs.Json.toJson(kind);
            result = negotiateContent(json, content);
        } else {
            result = Results.notFound();
        }
        return result;
    }

    public Result updateKind(Integer kindId) {
        Result result;
        Optional<String> optional = request().contentType();

        if (optional.isPresent() && optional.get().equals(Http.MimeTypes.JSON)) {
            JsonNode jsonNode = request().body().asJson();
            Form<Kind> kindForm = formFactory.form(Kind.class).bind(jsonNode);

            if (!kindForm.hasErrors()) {
                Kind kind = kindForm.get();
                kind.update();

                Content content = views.xml.kind.kind.render(kind);
                JsonNode json = play.libs.Json.toJson(kind);
                result = negotiateContent(json, content);
            } else {
                result = Results.badRequest(kindForm.errorsAsJson());
            }
        } else {
            result = Results.notAcceptable("Not Acceptable");
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

    public Result listKinds() {
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
}