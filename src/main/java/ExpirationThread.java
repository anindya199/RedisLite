public class ExpirationThread extends Thread {

    private final Database db;
    private volatile boolean isRunning = true;
    public ExpirationThread(Database db) {
        this.db = db;
    }
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    @Override
    public void run() {
        while (isRunning) {
            try {
                db.deleteExpiredKeys();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}