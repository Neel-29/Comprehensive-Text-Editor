import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testMain() {
        // Assuming App has a method called getGreeting that returns a greeting message
        App app = new App();
        String expectedGreeting = "Hello, World!";
        assertEquals(expectedGreeting, app.getGreeting());
    }
}