package Model;

import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.model.v26.segment.PID;

public class ORUUtils {
	
	
	
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

}
