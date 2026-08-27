
import java.util.HashMap;
import java.util.Set;

public class Database {
    private HashMap<String, String> storage;
    public Database() {
        storage = new HashMap<>();
    }

    public void SET(String key, String value) {
        storage.put(key, value);
    }
    public  String GET(String key) {

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
}
