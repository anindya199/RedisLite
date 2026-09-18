
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private final ConcurrentHashMap<String,Value> storage;
    private final LRUCache cache;
    public Database() {
        storage = new ConcurrentHashMap<>();
        cache = new LRUCache(5);
    }

    public void SET(String key, Value value) {
        storage.put(key, value);
        cache.put(key,true);
    }
    public Value GET(String key) {

        Value value = storage.get(key);

        if(value == null) {
            return null;
        }

        if(cache.containsKey(key))
            cache.get(key);
        else{
            cache.put(key, true);
        }
        return value;
    }
    public void DELETE(String key) {
        storage.remove(key);
        cache.remove(key);
    }
    public boolean EXISTS(String key) {
        return storage.containsKey(key);
    }
    public Set<String> KEYS() {
        return storage.keySet();
    }
    public void CLEAR() {
        storage.clear();
        cache.clear();
    }
    public Map<String, Value> getStorage() {
        return storage;
    }
    public synchronized void deleteExpiredKeys() {
        List<String> expiredKeys = new ArrayList<>();
        for (String key : KEYS()) {
            Value val = storage.get(key);
            if(val != null && val.isExpired()){
                expiredKeys.add(key);
            }
        }

        for(String expiredKey : expiredKeys){
            storage.remove(expiredKey);
            cache.remove(expiredKey);
        }
    }

    public Set<String> getCacheKeys() {
        return cache.keySet();
    }

    public int getCacheSize() {
        return cache.size();
    }

    public int getDatabaseSize() {
        return storage.size();
    }
}

