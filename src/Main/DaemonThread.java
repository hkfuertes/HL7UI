package Main;

import Graphics.MainFrame;
import MirthConnect.ClientConnector;
import Serial.MLLPConnector;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.impl.NoValidation;

/**
 * Created by hkfuertes on 20/05/16.
 */
public class DaemonThread extends Thread implements MLLPConnector.MLLPLiestner {

    ClientConnector client;
    ca.uhn.hl7v2.model.v26.message.ORU_R01 current;
    private MainFrame mainui;


    public void connectToServer(String addr, int port) {
        client = new ClientConnector(addr, port);
        client.startDaemon();
    }

    public void disconnectFromServer() {
        client.stopDaemon();
    }

    @Override
    public void run() {
        MLLPConnector connector = new MLLPConnector();
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
            printDataToGUI(current);
        }

    }

    public static Message parse(String message) {
        Message msg = null;
        HapiContext context = new DefaultHapiContext();
        context.setModelClassFactory(new CanonicalModelClassFactory("2.6"));
        context.setValidationContext(new NoValidation());
        PipeParser parser = context.getPipeParser();
        try {
            msg = parser.parse(message);
        } catch (HL7Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public MainFrame getView(){
        if(mainui != null){
            return mainui;
        }else{
            mainui = new MainFrame();
            mainui.registerDaemon(this);
            return mainui;
        }
    }
    
    private void printDataToGUI(ORU_R01 msg){
    	if(mainui != null)
    		mainui.printMessage(msg);
    }
    
}
