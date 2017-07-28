import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test extends Thread {
    public static void main(String[] args) {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Test(), 5000, 1000, TimeUnit.MILLISECONDS);
    }

    public void run() {
        System.out.println("hhhh");
    }
}
