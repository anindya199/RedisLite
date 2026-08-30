import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class TheMain {
    public static void main(String[] args) throws IOException{
        Persistence p = new  Persistence();
        Database db = new Database();
        try{
            p.readFromFile(db.getStorage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new  Scanner(System.in);

        boolean flag = true;
        while(flag) {
            String input = sc.nextLine();
            if(input.trim().isEmpty()) {
                continue;
            }
            List<String> words = List.of(input.split("\\s+"));

            switch (words.get(0)) {
                case "SET": {
                    if(words.size() < 3)
                        System.out.println("Error: SET requires a Key and a Value");
                    else
                        db.SET(words.get(1), words.get(2));
                    break;
                }
                case "GET": {
                    if(words.size()<2)
                        System.out.println("Error: GET requires a KEY");
                    else
                        System.out.println(db.GET(words.get(1)));
                    break;
                }
                case "DELETE": {
                    if(words.size()<2)
                        System.out.println("Error: DELETE requires a KEY");
                    else
                        db.DELETE(words.get(1));
                    break;
                }
                case "EXISTS": {
                    if(words.size()<2)
                        System.out.println("Error: EXISTS requires a KEY");
                    else
                        System.out.println(db.EXISTS(words.get(1)));
                    break;
                }
                case "EXIT":{
                    flag = false;
                    break;
                }
                case "KEYS": {
                    System.out.println(db.KEYS());
                    break;
                }
                case "CLEAR": {
                    db.CLEAR();
                    break;
                }
                default:{
                    System.out.println("Invalid command");
                }
            }
        }
        //write
        try {
            p.createFile();
            p.writeToFile(db.getStorage());
        }
        catch (IOException e) {
            throw  new IOException("Unable to create file");
        }

    }
}
