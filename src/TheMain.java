public class TheMain {
    public static void main(String[] args) {
        Database db = new Database();
        db.SET("name","Anindya");
        System.out.println(db.GET("name"));
        System.out.println(db.EXISTS("name"));
        db.DELETE("name");
        System.out.println(db.EXISTS("name"));

    }
}
