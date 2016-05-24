package Graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JList;

import Model.ORUUtils;
import Model.Paciente;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;

public class MultiEKGInternalFrame extends JInternalFrame{
	ArrayList<EKGPanel> pacientes = new ArrayList<EKGPanel>();
	
	public MultiEKGInternalFrame() {
		super("Multi ECG", true, true, true, true);

		this.setSize(new Dimension(400, 300));
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	}
	
	public void printMessage(ORU_R01 message) {
		Paciente paciente = ORUUtils.getPaciente(message);
		EKGPanel ep = getPanel(paciente);
		if (ep == null) {
			ep = new EKGPanel(paciente);
			pacientes.add(ep);
			add(ep);
			this.repaint();
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
