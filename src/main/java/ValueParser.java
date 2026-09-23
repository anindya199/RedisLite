//This function is used to find the exact data type of the value in CLI
public class ValueParser {
    public static ValueType detectType(String value) {

        try {
            Integer.parseInt(value);
            return ValueType.INTEGER;
        } catch (NumberFormatException e) {
        }

        try {
            Double.parseDouble(value);
            return ValueType.DOUBLE;
        } catch (NumberFormatException e) {
        }

        if (value.equalsIgnoreCase("true")
                || value.equalsIgnoreCase("false")) {
            return ValueType.BOOLEAN;
        }

        return ValueType.STRING;
    }
}