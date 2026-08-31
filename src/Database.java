
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Database {
    private final Map<String,Value> storage;
    public Database() {
        storage = new HashMap<>();
    }

    public void SET(String key, Value value) {
        storage.put(key, value);
    }
    public  Value GET(String key) {
        return storage.get(key);
    }
    public void DELETE(String key) {
        storage.remove(key);
    }
    public boolean EXISTS(String key) {
        return storage.containsKey(key);
    }
    public Set<String> KEYS() {
        return storage.keySet();
    }
    public void CLEAR() {
        storage.clear();
    }
    public Map<String, Value> getStorage() {
        return storage;
    }

}
