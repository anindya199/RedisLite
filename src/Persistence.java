import java.io.*;
import java.util.Map;

public class Persistence {
    File file = new File("text.txt");
    public void createFile() throws IOException,RuntimeException {
        file.createNewFile();
    }
    public void writeToFile(Map<String,Value> storage) throws IOException {
        try {
            FileWriter fw = new FileWriter(file);
            storage.forEach((k, v) ->
            {
                try {
                    fw.write(k);
                    fw.write("->");
                    fw.write(v.getType().name());
                    fw.write("->");
                    fw.write(v.getValue().toString());//Value -> String
                    fw.write("\n");
                }
                catch (IOException e) {
                    throw new RuntimeException("Error writing to file");
                }
            });
            fw.close();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error writing to file");
        }
    }


    public void readFromFile(Map<String,Value>storage) throws IOException,FileNotFoundException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String data;
            while ((data = br.readLine()) != null) {
                String[] val = data.split("->");
                Object value = getObject(val);
                //here the 1st parameter converts the datatype of the value to the enum(ValueType) type
                Value result = new Value(ValueType.valueOf(val[1]),value);
                storage.put(val[0],result);
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            return;
        }
    }
    //This method returns the value of the key-value pair not valueType
    private static Object getObject(String[] val) {
        ValueType type = ValueType.valueOf(val[1]);
        String rawValue = val[2];

        Object value;

        if (type == ValueType.INTEGER) {
            value = Integer.parseInt(rawValue);
        }
        else if (type == ValueType.DOUBLE) {
            value = Double.parseDouble(rawValue);
        }
        else if (type == ValueType.BOOLEAN) {
            value = Boolean.parseBoolean(rawValue);
        }
        else {
            value = rawValue;
        }

        return value;
    }

    public static Object getObject(ValueType type,String val) {
        Object value;

        if (type == ValueType.INTEGER) {
            value = Integer.parseInt(val);
        }
        else if (type == ValueType.DOUBLE) {
            value = Double.parseDouble(val);
        }
        else if (type == ValueType.BOOLEAN) {
            value = Boolean.parseBoolean(val);
        }
        else {
            value = val;
        }

        return value;
    }
}
