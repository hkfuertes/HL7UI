package Serial;

import java.util.Random;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v26.datatype.NM;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.model.v26.segment.MSH;
import ca.uhn.hl7v2.model.v26.segment.OBR;
import ca.uhn.hl7v2.model.v26.segment.OBX;
import ca.uhn.hl7v2.model.v26.segment.PID;
import ca.uhn.hl7v2.parser.Parser;


public class VirtualSerialConexion extends SerialConexion implements Runnable {
	Thread virtualComm;
	
    public void initialize() {
        virtualComm = new Thread(this);
        virtualComm.start();
    }


    public synchronized void close() {
        virtualComm.interrupt();
    }

	@Override
	public void run() {
		try {
			int contador = 0;
			while(virtualComm.isAlive()){
				String[] message = (
						MLLPConnector.START_ELEMENT+
						testMessage(contador++, contador % 200 == 0)+
						MLLPConnector.END_ELEMENT+
						MLLPConnector.END_ELEMENT_2
						).split("\n");
				for(String line: message){
					line += "\n";
					if (spListener != null){
						for(char c : line.toCharArray()){
							spListener.onCharReaded(c);
						}
						spListener.onLineReaded(line);
					}
				}
				virtualComm.sleep(20);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String testMessage(int contador, boolean hearRateOnly){
		float minX = 0, maxX = 0.7f;
		Random rand = new Random();
		float finalX = rand.nextFloat() * (maxX - minX) + minX;
		
		String message = "MSH|^~\\&|PC25ED||||20160523154252.905+0200||ORU^R01^ORU_R01|"+contador+"|P|2.6|||AL|AL|||||PC25\r\n"+
		"PID||PC25^^^&UPNA|||Fuertes^Miguel^J\r\n"+
		"OBR|1|PC25|PC25|29274-8^Vital signs measurements^LN\r\n";
		message += "OBX|1|NM|76056-1^ST amplitude.lead aVF^LN|1|"+finalX+"|mv^Mili volts^UCUM|||||N\r\n";
		if(!hearRateOnly){
			message += "OBX|2|NM|8310-5^TEMPERATURA CORPORAL^LN|1|37.5|Cel^GRADOS CENTIGRADO^UCUM|||||N\r\n"+
			"OBX|3|NM|8867-4^PULSO CORPORAL^LN|1|70|{beats}/min^PULSO POR MINUTO^UCUM|||||N\r\n"+
			"OBX|4|NM|20564-1^OXIMETRIA CORPORAL^LN|1|98|%^SATURACION EN SANGRE^UCUM|||||N\r\n";
		}
		return message;
	}

}
