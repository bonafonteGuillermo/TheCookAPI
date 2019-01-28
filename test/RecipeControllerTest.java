import org.junit.Before;
import org.junit.Test;
import play.mvc.Http;
import play.test.Helpers;

import static play.test.Helpers.fakeRequest;

public class RecipeControllerTest {

    @Before
    public void setUp() {
        Http.Context context = Helpers.httpContext();
        Http.Context.current.set(context);
    }

    @Test
    public void miTest(){
        Http.RequestBuilder request = fakeRequest("GET", "/recipes");
        Helpers.route(Helpers.fakeApplication(),request);

//        assertEquals(OK, result.status());
//        assertEquals("text/html", result.contentType().get());
//        assertEquals("utf-8", result.charset().get());
//        assertTrue(contentAsString(result).contains("Welcome"));
    }


}
