import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<String, Boolean> {
    //LRU logic here
    private final int capacity;
    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
        return size() > capacity;
    }
}

