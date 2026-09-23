import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class TheMain {
    public static void main(String[] args){
        Persistence p = new  Persistence();
        Database db = new Database();
        ExpirationThread t = new ExpirationThread(db);
        t.start();


        //READ FROM THE FILE (TEXT.TXT)
        p.createFile();
        p.readFromFile(db.getStorage());
        System.out.printf("[INFO] Loaded %d records from disk%n", db.getDatabaseSize());

        //START OF INPUT
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
                        System.out.println("[WARN] SET requires a Key and a Value");
                    else {
                        ValueType valType = ValueParser.detectType(words.get(2));

                        if (words.size() == 3) {
                            Value result = new Value(valType, Persistence.getObject(valType, words.get(2)));
                            db.SET(words.get(1), result);
                        }
                        else if (words.size() == 5 && words.get(3).equalsIgnoreCase("EX")) {
                            try{
                                long ttl = Long.parseLong(words.get(4));
                                if(ttl>0) {
                                    Value result = new Value(valType, Persistence.getObject(valType, words.get(2)), ttl);
                                    db.SET(words.get(1), result);
                                }
                                else
                                    System.out.println("[WARN] Expiration Time must be greater than 0");
                            }
                            catch (NumberFormatException e){
                                System.out.println("[WARN] TTL must be a Number");
                            }
                        }
                        else {
                            System.out.println("[WARN] The syntax is Invalid");
                        }
                    }
                    break;
                }


                case "GET": {
                    if(words.size()<2)
                        System.out.println("[WARN] GET requires a KEY");
                    else{
                        Value value = db.GET(words.get(1));
                        if(value!=null)
                            System.out.println(value.getValue());
                        else
                            System.out.println("null");
                    }
                    break;
                }


                case "DELETE": {
                    if(words.size()<2)
                        System.out.println("[WARN] DELETE requires a KEY");
                    else {
                        boolean deleted = db.DELETE(words.get(1));
                        if(deleted)
                            System.out.println("Success");
                        else
                            System.out.println("[WARN] Key not found");
                    }
                    break;
                }


                case "EXISTS": {
                    if(words.size()<2)
                        System.out.println("[WARN] EXISTS requires a KEY");
                    else
                        System.out.println(db.EXISTS(words.get(1)));
                    break;
                }


                case "EXIT":{
                    System.out.println("[INFO] Shutting down...");
                    flag = false;
                    t.setIsRunning(false);
                    t.interrupt();

                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                }


                case "KEYS": {
                    System.out.println(db.KEYS());
                    System.out.println(db.getCacheKeys());
                    break;
                }


                case "CLEAR": {
                    db.CLEAR();
                    break;
                }


                case "EXPIRE":{
                    if(words.size() != 3)
                        System.out.println("[WARN] EXPIRE requires a Key and a Value");
                    else {
                        try{
                            long ttl = Long.parseLong(words.get(2));
                            if(ttl>0) {
                                Value val = db.GET(words.get(1));
                                if (val != null) {
                                    val.setExpireAt(ttl);
                                } else {
                                    System.out.println("[WARN] Value not found");
                                }
                            }
                            else
                                System.out.println("[WARN] Expiration Time must be greater than 0");
                        }
                        catch (NumberFormatException e){
                            System.out.println("[WARN] TTL must be a Number");
                        }
                    }
                    break;
                }


                case "TTL":{
                    if(words.size()!=2)
                        System.out.println("[WARN] TTL requires a Key");//if the length of the command is not 2
                    else {
                        Value value = db.GET(words.get(1));
                        if(value==null)
                            System.out.println("[WARN] Key does not exist");//if the given key is wrong

                        else if(value.getRemainingTTL() == -1)
                            System.out.println("[WARN] The Key has no expiration");

                        else
                            System.out.println(value.getRemainingTTL());

                    }
                    break;
                }


                default:{
                    System.out.println("[WARN] Invalid command");
                }
            }
        }
        sc.close();
        // WRITE TO THE FILE(TEXT.TXT)
        p.createFile();
        p.writeToFile(db.getStorage());

    }
}
