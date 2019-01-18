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
        Optional<String> optional = request().contentType();
        if (!optional.isPresent() || !optional.get().equals(Http.MimeTypes.JSON)) {
            return Results.notAcceptable("Not Acceptable");
        }

        JsonNode jsonNode = request().body().asJson();
        Form<Kind> kindForm = formFactory.form(Kind.class).bind(jsonNode);
        if (kindForm.hasErrors()) {
            return Results.badRequest(kindForm.errorsAsJson());
        }

        Kind kindToCreate = kindForm.get();
        if (Kind.findByName(kindToCreate.getName()) != null) {
            Results.status(CONFLICT);
        }

        kindToCreate.save();

        Content content = views.xml.kind.kind.render(kindToCreate);
        JsonNode json = play.libs.Json.toJson(kindToCreate);
        return negotiateContent(json, content);
    }

    public Result retrieveKind(Integer kindId) {
        Kind kind = Kind.findById(kindId.longValue());
        if (kind == null) {
            return Results.notFound();
        }

        Content content = views.xml.kind.kind.render(kind);
        JsonNode json = play.libs.Json.toJson(kind);
        return negotiateContent(json, content);
    }

    public Result updateKind(Integer kindId) {
        Optional<String> optional = request().contentType();
        if (!optional.isPresent() || !optional.get().equals(Http.MimeTypes.JSON)) {
            return Results.notAcceptable("Not Acceptable");
        }

        JsonNode jsonNode = request().body().asJson();
        Form<Kind> kindForm = formFactory.form(Kind.class).bind(jsonNode);
        if (kindForm.hasErrors()) {
            return Results.badRequest(kindForm.errorsAsJson());
        }

        Kind kind = kindForm.get();
        if (Kind.findById(kind.getId()) == null) {
            return Results.notFound();
        }

        kind.update();

        Content content = views.xml.kind.kind.render(kind);
        JsonNode json = play.libs.Json.toJson(kind);
        return negotiateContent(json, content);
    }

    public Result deleteKind(Integer kindId) {
        Kind kind = Kind.findById(kindId.longValue());
        if (kind == null || !kind.delete()) {
            return Results.notFound();
        }
        return ok();
    }

    public Result deleteAllKinds() {
        int affectedRows = Kind.deleteAll();
        if (affectedRows == 0) {
            return Results.notFound();
        }
        return ok();
    }

    public Result listKinds() {
        List<Kind> kindList = Kind.findAll();
        if (kindList.isEmpty()) {
            return Results.notFound();
        }

        Content content = views.xml.kind.kinds.render(kindList);
        JsonNode json = play.libs.Json.toJson(kindList);
        return negotiateContent(json, content);
    }
}