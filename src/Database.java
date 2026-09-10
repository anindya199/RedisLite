
import java.util.*;

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
    public void deleteExpiredKeys() {
        List<String> expiredKeys = new ArrayList<>();
        for (String key : KEYS()) {
            Value val = storage.get(key);
            if(val.isExpired()){
                expiredKeys.add(key);
            }
        }

        for(String expiredKey : expiredKeys){
            storage.remove(expiredKey);
        }
    }
}

