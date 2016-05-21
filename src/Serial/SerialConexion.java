package Serial;


import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;


public class SerialConexion implements SerialPortEventListener {

    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyACM0", // Raspberry Pi
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
            "/dev/tty.wchusbserialfa130",
            "/dev/tty.wchusbserialfd120"
    };

    private static final int TIME_OUT = 2000;

    private static final int DATA_RATE = 9600;
    SerialPort serialPort;

    private BufferedReader input;

    private OutputStream output;


    public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }


    public synchronized void serialEvent(SerialPortEvent oEvent) {
        char character;
        String line = "";
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {

                while ((character = (char) input.read()) != '\n') {
                    line += character;
                    if (spListener != null)
                        spListener.onCharReaded(character);
                }

                if (spListener != null) {
                    spListener.onCharReaded('\n');
                    spListener.onLineReaded(line);
                    line = "";
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    //Interface for getting Data from Port.
    public interface SerialPortListener {
        void onLineReaded(String line);

        void onCharReaded(char character);
    }

    SerialPortListener spListener = null;

    public void setSerialPortListener(SerialPortListener spListener) {
        this.spListener = spListener;
    }
}
