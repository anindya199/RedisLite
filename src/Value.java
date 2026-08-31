public class Value {
    private final ValueType type;
    private final Object value;

    public Value(ValueType type, Object value) {
        this.type = type;
        this.value = value;
    }
    public ValueType getType(){
        return type;
    }
    public Object getValue(){
        return value;
    }
}