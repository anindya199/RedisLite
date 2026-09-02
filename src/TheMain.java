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
            return;
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
                    if (words.size() < 3)
                        System.out.println("Error: SET requires a Key and a Value");
                    else {
                        ValueType valType = ValueParser.detectType(words.get(2));

                        if (words.size() == 3) {
                            Object val = words.get(2);
                            Value result = new Value(valType, Persistence.getObject(valType, words.get(2)));
                            db.SET(words.get(1), result);
                        }
                        else if (words.size() == 5 && words.get(3).equalsIgnoreCase("EX")) {
                            try{
                                long ttl = Long.parseLong(words.get(4));
                                Value result = new Value(valType,Persistence.getObject(valType, words.get(2)),ttl);
                                db.SET(words.get(1), result);
                            }
                            catch (NumberFormatException e){
                                System.out.println("Error: The required type of EX to be a Number");
                            }
                        }
                        else {
                            System.out.println("Error: The syntax is Invalid");
                        }
                    }
                    break;
                }


                case "GET": {
                    if(words.size()<2)
                        System.out.println("Error: GET requires a KEY");
                    else{
                        Value value = db.GET(words.get(1));
                        if(value!=null)
                            System.out.println(value.getValue());
                    }
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


                case "EXPIRE":{
                    if(words.size() != 3)
                        System.out.println("Error: EXPIRE requires a Key and a Value");
                    else {
                        try{
                            long ttl = Long.parseLong(words.get(2));
                            Value val = db.GET(words.get(1));
                            if(val!=null){
                                val.setExpireAt(ttl);
                            }
                            else{
                                System.out.println("Error: Value not found");
                            }
                        }
                        catch (NumberFormatException e){
                            System.out.println("Error: The required type of EX to be a Number");
                        }
                    }
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
