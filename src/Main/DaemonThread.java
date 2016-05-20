package Main;

import MirthConnect.ClientConnector;
import Serial.MLLPConnector;
import Serial.SerialConexion;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.parser.PipeParser;

/**
 * Created by hkfuertes on 20/05/16.
 */
public class DaemonThread extends Thread implements MLLPConnector.MLLPLiestner {

    ClientConnector client;
    ca.uhn.hl7v2.model.v26.message.ORU_R01 current;


    public void connectToServer(String addr, int port) {
        client = new ClientConnector(addr, port);
        client.startDaemon();
    }

    public void disconnectFromServer() {
        client.stopDaemon();
    }

    @Override
    public void run() {
        SerialConexion sc = new SerialConexion();
        sc.initialize();
        MLLPConnector connector = new MLLPConnector(sc);
        connector.setMLLPListener(this);
    }

    @Override
    public void onMessageReceived(String message) {

        //System.out.println(message);
        if (client != null && client.isSendable())
            client.send(MLLPConnector.encapsulateMessage(message));

        //Only validate 2.6, ORU_R01
        Message msg = parse(message);
        if (msg instanceof ca.uhn.hl7v2.model.v26.message.ORU_R01) {
            current = (ORU_R01) msg;
        }

    }

    public static Message parse(String message) {
        Message msg = null;
        HapiContext context = new DefaultHapiContext();
        PipeParser parser = context.getPipeParser();
        try {
            msg = parser.parse(message);
        } catch (HL7Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}
