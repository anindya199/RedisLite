import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TheMain {
    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        Database db = new Database();
        boolean flag = true;
        while(flag) {
            String input = sc.nextLine();
            List<String> words = List.of(input.split("\\s+"));


            switch (words.get(0)) {
                case "SET": {
                    db.SET(words.get(1), words.get(2));
                    break;
                }
                case "GET": {
                    System.out.println(db.GET(words.get(1)));
                    break;
                }
                case "DELETE": {
                    db.DELETE(words.get(1));
                    break;
                }
                case "EXISTS": {
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
    }
}
