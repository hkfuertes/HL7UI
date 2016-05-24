package Graphics;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JInternalFrame;

import Model.ORUUtils;
import Model.Paciente;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;

public class MultiEKGInternalFrame extends JInternalFrame{
	ArrayList<EKGPanel> pacientes = new ArrayList<EKGPanel>();
	
	public void printMessage(ORU_R01 message) {
		Paciente paciente = ORUUtils.getPaciente(message);
		EKGPanel ep = getPanel(paciente);
		if (ep == null) {
			ep = new EKGPanel(paciente);
			pacientes.add(ep);
			add(ep);
		}
		ep.printMessage(message);
		
	}

	private EKGPanel getPanel(Paciente paciente) {
		for(EKGPanel p : pacientes){
			if(p.getPaciente().equals(paciente))
				return p;
		}
		return null;
	}

}
