import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.test.Helpers;
import play.test.FakeApplication;

//TODO implement
public class BaseModelTest {
    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
