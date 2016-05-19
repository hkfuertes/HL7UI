package MirthConnect;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class ServerConnector implements Runnable {

    private Socket clientSocket = null;
    private PrintWriter out;
    private ServerSocket serverSocket;
    private int port;
    private Thread connectorThread;

    public void listen(int port) {
        if (connectorThread != null && connectorThread.isAlive())
            connectorThread.interrupt();
        this.port = port;
        connectorThread = new Thread(this);
        connectorThread.start();
    }

    public void send(String message) {
        if (clientSocket != null && clientSocket.isConnected()) {
            out.print(message);
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception ex) {
            this.run();
        }
    }
}
