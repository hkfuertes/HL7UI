package Serial;

import Serial.SerialConexion.SerialPortListener;

/**
 * Created by hkfuertes on 20/05/16.
 */
public class MLLPConnector implements SerialPortListener {
    public static final char START_ELEMENT = 0x0b;
    public static final char END_ELEMENT = 0x1c;//0d;
    public static final char END_ELEMENT_2 = 0x0d;//0d;
    private SerialConexion sc;

    MLLPLiestner listener;
    String current = null;
    
    public MLLPConnector(){
    	this(new SerialConexion());
    }

    public MLLPConnector(SerialConexion sc) {
        this.sc = sc;
        this.sc.setSerialPortListener(this);
        this.sc.initialize();
    }

    //Nothing to do with this.
    @Override
    public void onLineReaded(String line) {
    }

    @Override
    public void onCharReaded(char character) {
        if (character == START_ELEMENT) {
            current = "";
        } else if (character == END_ELEMENT) {
            sendAndClear();
        } else {
            if (current != null)
                current += character;
        }
    }


    public interface MLLPLiestner {
        void onMessageReceived(String message);
    }

    public void setMLLPListener(MLLPLiestner listener) {
        this.listener = listener;
    }

    private void sendAndClear() {
        if (this.listener != null)
            listener.onMessageReceived(current);
        current = null;
    }


    public static String encapsulateMessage(String message) {
        return START_ELEMENT + message + END_ELEMENT + END_ELEMENT_2;
    }

}
