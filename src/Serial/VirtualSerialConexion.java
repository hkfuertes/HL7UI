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
				String[] message = MLLPConnector.encapsulateMessage(testMessage(contador++)).split("\n");
				for(String line: message){
					line += "\n";
					if (spListener != null){
						for(char c : line.toCharArray()){
							spListener.onCharReaded(c);
						}
						spListener.onLineReaded(line);
					}
				}
				virtualComm.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String testMessage(int contador){
		Random rand = new Random();
		String message = "MSH|^~\\&|PC25ED||||20160523154252.905+0200||ORU^R01^ORU_R01|"+contador+"|P|2.6|||AL|AL|||||PC25\r\n";
		if(rand.nextBoolean() || true)
			message += "PID||PC25^^^&UPNA|||Fuertes^Miguel^J\r\n";
		else
			message += "PID||PC26^^^&UPNA|||Fuertes^Javier^J\r\n";
		message += "OBR|1|PC25|PC25|29274-8^Vital signs measurements^LN\r\n";
		message += "OBX|1|NM|8310-5^TEMPERATURA CORPORAL^LN|1|37.5|Cel^GRADOS CENTIGRADO^UCUM|||||N\r\n";
		message += "OBX|2|NM|8867-4^PULSO CORPORAL^LN|1|70|{beats}/min^PULSO POR MINUTO^UCUM|||||N\r\n";
		message += "OBX|3|NM|20564-1^OXIMETRIA CORPORAL^LN|1|98|%^SATURACION EN SANGRE^UCUM|||||N\r\n";
		
		// Observacion nueva, dos ultimos campos son inicio y fin de las observaciones.
		//message += "OBR|2||09780979a9879^ACME HEALTH^ABCD002343785379^EUI-64|WAVEFORMBOUNDED|||20080515121000.100-400|20080515121001.100-400";
		//The actual waveform raw data, as delimited signed integers 
		message += "OBX|4|NA|149504^ MDC_PULS_OXIM_PLETH^MDC |1|1027^3504^4586^6612^8234^10592^11250^12183^11490||||||||| 20080515121000.100\r\n";
		// Sample rate is 50 samples/sec. MDC code is 262144+2464
		message += "OBX|5|NM|0^MDC_ATTR_SAMP_RATE^MDC |1|200|264608^MDC_DIM_PER_SEC\r\n";
		return message;
	}

}
