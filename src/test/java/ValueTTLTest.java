import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ValueTTLTest {

    @Test
    public void testValue() {
        Value val1 = new Value(ValueType.STRING, "Anindya", 3);
        Value val2 = new Value(ValueType.STRING, "Anindya", -1);
        assertEquals("Anindya", val1.getValue());
        assertEquals(ValueType.STRING, val1.getType());
        long ttl1 = val1.getRemainingTTL();

        assertTrue(ttl1 > 0);
        assertTrue(ttl1 <= 3);



        assertEquals("Anindya", val2.getValue());
        assertEquals(ValueType.STRING, val2.getType());
        long ttl2 = val2.getRemainingTTL();

        assertFalse(ttl2 > 0 && ttl2 <= 3);

    }
    @Test
    public void setExpiredAtWithTTL(){

        Value val1 = new Value(ValueType.STRING, "Anindya");

        val1.setExpireAt(3);

        long ttl1 = val1.getRemainingTTL();
        assertTrue(ttl1 > 0);
        assertTrue(ttl1 <= 3);
    }

    @Test
    public void set_ExpiredAt_TTL_With_NegativeValues(){

        Value val1 = new Value(ValueType.STRING, "Anindya");
        val1.setExpireAt(-1);
        long ttl1 = val1.getRemainingTTL();

        assertEquals(-1,ttl1);
    }

    @Test
    public void setExpiredAt_After_Expiration() throws InterruptedException {
        Value val = new Value(ValueType.STRING, "Anindya");
        val.setExpireAt(1);

        Thread.sleep(1100);
        assertTrue(val.isExpired());
    }
}