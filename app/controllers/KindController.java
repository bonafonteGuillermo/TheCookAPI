package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Kind;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

import static utils.Constants.*;
import static utils.Utils.*;

public class KindController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result createKind() {
        if (!isContentTypeJSON(request())) return Results.notAcceptable(getMessage(MESSAGE_NOT_ACCEPTABLE));

        JsonNode jsonNode = request().body().asJson();
        Form<Kind> kindForm = formFactory.form(Kind.class).bind(jsonNode);
        if (kindForm.hasErrors()) {
            return Results.badRequest(kindForm.errorsAsJson());
        }

        Kind kindToCreate = kindForm.get();
        if (Kind.findByName(kindToCreate.getName()) != null) {
            return Results.status(CONFLICT, getMessage(MESSAGE_KIND_CONFLICT));
        }

        kindToCreate.save();

        Content content = views.xml.kind.kind.render(kindToCreate);
        JsonNode json = play.libs.Json.toJson(kindToCreate);
        return negotiateContent(json, content);
    }

    public Result retrieveKind(Integer kindId) {
        Kind kind = Kind.findById(kindId.longValue());
        if (kind == null) {
            return Results.notFound(getMessage(MESSAGE_KIND_NOTFOUND));
        }

        Content content = views.xml.kind.kind.render(kind);
        JsonNode json = play.libs.Json.toJson(kind);
        return negotiateContent(json, content);
    }

    public Result updateKind(Integer kindId) {
        if (!isContentTypeJSON(request())) return Results.notAcceptable(getMessage(MESSAGE_NOT_ACCEPTABLE));

        JsonNode jsonNode = request().body().asJson();
        Form<Kind> kindForm = formFactory.form(Kind.class).bind(jsonNode);
        if (kindForm.hasErrors()) {
            return Results.badRequest(kindForm.errorsAsJson());
        }

        if (Kind.findById(kindId.longValue()) == null) {
            return Results.notFound(getMessage(MESSAGE_KIND_NOTFOUND));
        }

        Kind newKind = kindForm.get();
        newKind.update();

        Content content = views.xml.kind.kind.render(newKind);
        JsonNode json = play.libs.Json.toJson(newKind);
        return negotiateContent(json, content);
    }

    public Result deleteKind(Integer kindId) {
        Kind kind = Kind.findById(kindId.longValue());
        if (kind == null || !kind.delete()) {
            return Results.notFound(getMessage(MESSAGE_KIND_NOTFOUND));
        }
        return ok(getMessage(MESSAGE_KIND_DELETED));
    }

    public Result deleteAllKinds() {
        int affectedRows = Kind.deleteAll();
        if (affectedRows == 0) {
            return Results.notFound(getMessage(MESSAGE_KIND_EMPTY));
        }
        return ok(getMessage(MESSAGE_ALL_KINDS_DELETED));
    }

    public Result listKinds() {
        List<Kind> kindList = Kind.findAll();
        if (kindList.isEmpty()) {
            return Results.notFound(getMessage(MESSAGE_KIND_EMPTY));
        }

        Content content = views.xml.kind.kinds.render(kindList);
        JsonNode json = play.libs.Json.toJson(kindList);
        return negotiateContent(json, content);
    }
}