import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadSafetyTest {
    @Test
    public void threadSafetyTest() throws InterruptedException {

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

        System.out.println("Cache Size = " + db.getCacheSize());
        assertTrue(db.getCacheSize() <= 5);
    }
}
