import java.io.*;
import java.util.Map;

public class Persistence {
    File file = new File("data.txt");
    public void createFile() throws IOException,RuntimeException {
        file.createNewFile();
    }
    public void writeToFile(Map<String,String> storage) throws IOException {
        try {
            FileWriter fw = new FileWriter(file);
            storage.forEach((k, v) ->
            {
                try {
                    fw.write(k);
                    fw.write("->");
                    fw.write(v);
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
    public void readFromFile(Map<String,String>storage) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;
        while ((data = br.readLine()) != null) {
            String[] val = data.split("->");
            storage.put(val[0],val[1]);
        }
        br.close();
    }
}
