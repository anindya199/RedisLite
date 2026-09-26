
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class IntegrationTest {

    @Test
    public void testIntegrationFlow(){

        Database database = new Database();

        database.SET("name",new Value(ValueType.STRING,"John"));

        assertTrue(database.EXISTS("name"));
        assertEquals("John",database.GET("name").getValue());
        assertTrue(database.DELETE("name"));
        assertFalse(database.EXISTS("name"));
        assertNull(database.GET("name"));
    }
}
