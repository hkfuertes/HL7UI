package Model;

import java.util.ArrayList;

import ca.uhn.hl7v2.model.v26.datatype.NM;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.model.v26.segment.MSH;
import ca.uhn.hl7v2.model.v26.segment.OBR;
import ca.uhn.hl7v2.model.v26.segment.OBX;
import ca.uhn.hl7v2.model.v26.segment.PID;

public class ORUUtils {
	
	public static Mensaje getMensaje(ORU_R01 oru){
		MSH mshSegment = oru.getMSH();
		Mensaje msg = new Mensaje();
		msg.endevice = mshSegment.getSendingApplication().getNamespaceID().getValue();
		msg.id_mensaje = mshSegment.getMessageControlID().getValue();
		msg.setHora(mshSegment.getDateTimeOfMessage().getValue());
		return msg;
	}
	
	
	public static Paciente getPaciente(ORU_R01 msg) {
		return getPaciente(msg.getPATIENT_RESULT().getPATIENT().getPID());
	}
	
	public static Paciente getPaciente(PID pid) {
		Paciente paciente = new Paciente();
		paciente.id = pid.getPatientID().getIDNumber().getValue();
		paciente.nombre = pid.getPatientName(0).getGivenName().getValue();
		paciente.apellidos = pid.getPatientName(0).getFamilyName().getSurname().getValue();
		paciente.centro = pid.getPatientID().getAssigningAuthority().getUniversalID().getValue();
		return paciente;
	}
	
	public static ArrayList<Observacion> getObservaciones(ORU_R01 oru){
		ArrayList<Observacion> obs = new ArrayList<Observacion> ();
		try{
			ORU_R01_ORDER_OBSERVATION orderObservation = oru.getPATIENT_RESULT().getORDER_OBSERVATION();

			for(ORU_R01_OBSERVATION obr : orderObservation.getOBSERVATIONAll()){
				Observacion ob = new Observacion();
				OBX obx = obr.getOBX();
				ob.tipo = obx.getObservationIdentifier().getIdentifier().getValue();
				ob.medida = ((NM)obx.getObservationValue(0).getData()).getValue();
				ob.unidades = obx.getUnits().getIdentifier().getValue();
				obs.add(ob);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return obs;
	}

}
