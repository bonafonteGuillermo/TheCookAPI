import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Recipe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.db.Database;
import play.db.Databases;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.test.Helpers.*;

//TODO implement
public class RecipeControllerTest {

    private final String GET = "GET";
    private final String POST = "POST";
    private final String UPDATE = "UPDATE";
    private final String DELETE = "DELETE";
    private final String RECIPE = "/recipe";
    private final String RECIPES = "/recipes";
    private final String RECIPES_SEARCH = "/recipes/search";

    public static Application app;
    private Database database;

    @Before
    public void setUp() {
        Http.Context context = Helpers.httpContext();
        Http.Context.current.set(context);
    }

    @After
    public void shutDownDB(){
        database.shutdown();
    }

    @Test
    public void testEmptyRecipes(){

        List<Recipe> recipeList = Recipe.findAll();
        assertEquals(recipeList.size(), 0);

        Recipe.findAll();
    }

    @Test
    public void miTest(){
//        Http.RequestBuilder request = fakeRequest("GET", "/recipes");
//        Helpers.route(Helpers.fakeApplication(),request);

//        assertEquals(OK, result.status());
//        assertEquals("text/html", result.contentType().get());
//        assertEquals("utf-8", result.charset().get());
//        assertTrue(contentAsString(result).contains("Welcome"));
    }
}