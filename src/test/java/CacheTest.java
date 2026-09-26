
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    LRUCache cache = new  LRUCache(3);

    @Test
    public void testLRUCache() {
        cache.put("A", true);
        cache.put("B", true);
        cache.put("C", true);

        cache.get("A");
        cache.put("D", true);

        assertTrue(cache.get("A"));
        assertNull(cache.get("B"));
        assertTrue(cache.get("C"));
        assertTrue(cache.get("D"));
        assertEquals(3, cache.size());


    }
}
