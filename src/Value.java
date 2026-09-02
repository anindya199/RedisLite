
public class Value {
    private final ValueType type;
    private final Object value;
    private long expireAt;
//    private long ttl;
    public Value(ValueType type, Object value) {
        this.type = type;
        this.value = value;
        expireAt = -1;
    }
    public Value(ValueType type, Object value,long ttl) {
        this.type = type;
        this.value = value;

        long currentTime = System.currentTimeMillis();
        expireAt = currentTime + ttl*1000;
    }
    public ValueType getType(){
        return type;
    }
    public Object getValue(){
        return value;
    }
    public boolean isExpired(){
        return expireAt != -1 && expireAt <= System.currentTimeMillis();
    }

    public void setExpireAt(long ttl) {
        long currentTime = System.currentTimeMillis();
        expireAt = currentTime + ttl*1000;
    }
}