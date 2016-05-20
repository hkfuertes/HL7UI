package Main;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class MainClass {

    public static void main(String[] args) throws Exception {
        DaemonThread thread = new DaemonThread();
        thread.start();
        //thread.connectToServer("localhost",6660);
        thread.join();

    }
}
