public class ThreadSafetyTest {

    public static void main(String[] args) throws InterruptedException {

        Database db = new Database();

        //Writer Thread
        Thread writer = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                db.SET("key" + i,
                        new Value(ValueType.STRING, "value"));
            }
        });

        //Reader Thread
        Thread reader = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                db.GET("key" + i);
            }
        });

        //Deleter Thread
        Thread deleter = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                db.DELETE("key" + i);
            }
        });

        writer.start();
        reader.start();
        deleter.start();

        writer.join();
        reader.join();
        deleter.join();

        System.out.println("Test Completed");
        System.out.println("Cache Size: " + db.getCacheSize());
        System.out.println("Database Size: " + db.getDatabaseSize());

        if (db.getCacheSize() > 5) {
            System.out.println("ERROR: Cache exceeded capacity!");
        } else {
            System.out.println("Cache capacity respected.");
        }

        System.out.println("Cache Keys: " + db.getCacheKeys());
    }
}
