
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
        synchronized (cache) {
            cache.put(key, true);
        }
    }
    public Value GET(String key) {

        Value value = storage.get(key);

        if(value == null) {
            return null;
        }
        synchronized (cache) {
            if (cache.containsKey(key))
                cache.get(key);
            else {
                cache.put(key, true);
            }
        }
        return value;
    }
    public boolean DELETE(String key) {
        Value value = storage.remove(key);
        synchronized (cache) {
            cache.remove(key);
        }

        return value != null;
    }
    public boolean EXISTS(String key) {
        return storage.containsKey(key);
    }
    public Set<String> KEYS() {
        return storage.keySet();
    }
    public void CLEAR() {
        storage.clear();
        synchronized(cache) {
            cache.clear();
        }
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
            synchronized (cache) {
                cache.remove(expiredKey);
            }
        }
    }

    public Set<String> getCacheKeys() {
        synchronized (cache) {
            return new HashSet<>(cache.keySet());

        }
    }

    public int getCacheSize() {
        synchronized (cache) {
            return cache.size();
        }
    }

    public int getDatabaseSize() {
        return storage.size();
    }
}

