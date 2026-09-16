public class ThreadSafetyTest {

    public static void main(String[] args) throws InterruptedException {

        Database db = new Database();

        Thread writer = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                db.SET("key" + i,
                        new Value(ValueType.STRING, "value"));
            }
        });

        Thread reader = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                db.GET("key" + i);
            }
        });

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
    }
}
