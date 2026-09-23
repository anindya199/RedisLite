import java.io.*;
import java.sql.SQLOutput;
import java.util.LinkedHashMap;
import java.util.Map;

public class Persistence {
    File file = new File("text.txt");
    public void createFile(){
        try {
            if (file.createNewFile()) {
                System.out.println("[INFO] Persistence file created");
            }
        }
        catch (IOException e) {
            System.out.println("[ERROR] Failed to create persistence file");
        }
    }



    public void writeToFile(Map<String,Value> storage){
        try(FileWriter fw = new FileWriter(file)) {
            for (Map.Entry<String, Value> entry : storage.entrySet())
            {
                fw.write(entry.getKey());
                fw.write("->");
                fw.write(entry.getValue().getType().name());
                fw.write("->");
                fw.write(entry.getValue().getValue().toString());//Value -> String
                fw.write("\n");
            };
            System.out.println("[INFO] Persistence data written to disk");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to write persistence file");
        }
    }


    public void readFromFile(Map<String,Value>storage) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){

            String data;
            while ((data = br.readLine()) != null) {

                String[] val = data.split("->",-1);
                if(val.length != 3){
                    System.out.printf("[WARN] Skipping malformed record -> %s%n", data);
                    continue;
                }

                if(val[0].isBlank()){
                    System.out.printf("[WARN] Empty key in record -> %s%n", data);
                    continue;
                }

                if(val[1].isBlank()){
                    System.out.printf("[WARN] Empty datatype in record -> %s%n", data);
                    continue;
                }

                if(val[2].isBlank()){
                    System.out.printf("[WARN] Empty value in record -> %s%n", data);
                    continue;
                }
                //here the 1st parameter converts the datatype of the value to the enum(ValueType) type
                try {
                    ValueType type =  ValueType.valueOf(val[1]);
                    Object value = getObject(type,val[2]);
                    Value result = new Value(type, value);
                    storage.put(val[0], result);
                } catch (IllegalArgumentException e) {
                    System.out.printf("[WARN] Invalid record -> %s%n", data);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("[INFO] No persistence file found");
        }
        catch (IOException e) {
            System.out.println("[ERROR] Failed to read persistence file");
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
