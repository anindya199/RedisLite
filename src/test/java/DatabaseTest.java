
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    public void testDatabaseSetAndGet() {

            Database database = new Database();


            database.SET("name", new Value(ValueType.STRING, "Anindya"));
            database.SET("age", new Value(ValueType.INTEGER, 10));
            database.SET("money", new Value(ValueType.DOUBLE, 19.63));
            database.SET("active", new Value(ValueType.BOOLEAN, true));

            assertEquals("Anindya",database.GET("name").getValue());
            assertEquals(10,database.GET("age").getValue());
            assertEquals(19.63,database.GET("money").getValue());
            assertEquals(true,database.GET("active").getValue());
    }

    @Test
    public void testDelete() {
        Database database = new Database();

        database.SET("name", new Value(ValueType.STRING, "Anindya"));
        database.SET("age", new Value(ValueType.INTEGER, 10));
        database.SET("money", new Value(ValueType.DOUBLE, 19.63));
        database.SET("active", new Value(ValueType.BOOLEAN, true));

        assertTrue(database.DELETE("name"));
        assertTrue(database.DELETE("age"));
        assertTrue(database.DELETE("money"));
        assertTrue(database.DELETE("active"));
        assertFalse(database.DELETE("unknown"));
    }


    @Test
    public void testEXISTS() {
        Database database = new Database();

        database.SET("name",new Value(ValueType.STRING,"Anindya"));
        database.SET("age",new Value(ValueType.INTEGER, 10));
        database.SET("money",new Value(ValueType.DOUBLE, 19.63));
        database.SET("active",new Value(ValueType.BOOLEAN, true));

        assertTrue(database.EXISTS("name"));
        assertTrue(database.EXISTS("age"));
        assertTrue(database.EXISTS("money"));
        assertTrue(database.EXISTS("active"));
        assertFalse(database.EXISTS("unknown"));
    }

}
