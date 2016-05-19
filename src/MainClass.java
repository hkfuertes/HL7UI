/**
 * Created by hkfuertes on 19/05/16.
 */
public class MainClass {

    public static void main(String[] args) throws Exception {

        SerialTest sc = new SerialTest();
        sc.initialize();
        Thread t = new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();

    }
}
