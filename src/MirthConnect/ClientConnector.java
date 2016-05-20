package MirthConnect;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class ClientConnector implements Runnable {

    private int port;
    private String addr;
    private PrintWriter writer;
    private Socket socket;
    private Thread clientThread;

    public ClientConnector(String addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    private void connect(String addr, int port) {
        try {
            socket = new Socket(addr, port);
            writer = new PrintWriter(new PrintStream(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void connect() {
        this.connect(this.addr, this.port);
    }

    public void send(String message) {
        if (socket != null && socket.isConnected()) {
            writer.print(message);
        }
    }

    public void startDaemon() {
        clientThread = new Thread(this);
        clientThread.start();
    }

    @Override
    public void run() {
        while (socket == null || !socket.isConnected()) {
            connect();
        }
    }

    public boolean isSendable() {
        return !(socket == null || !socket.isConnected());
    }

    public void stopDaemon() {
        clientThread.interrupt();
    }
}
