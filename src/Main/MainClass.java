package Main;

import MirthConnect.ClientConnector;
import Model.HL7Message;
import Serial.HL7SerialConnector;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class MainClass {

    public static void main(String[] args) throws Exception {

        //MainGUI gui = new MainGUI("Arduino Comunicator");

        ClientConnector cc = new ClientConnector("localhost", 6661);
        cc.startDaemon();

        HL7SerialConnector hl7SerialConnector = new HL7SerialConnector();
        hl7SerialConnector.setHL7Listener(new HL7SerialConnector.HL7Listener() {
            @Override
            public void onMessageReceived(HL7Message message) {
                System.out.println(message);
                cc.send(message.rawString());
            }
        });

    }
}
