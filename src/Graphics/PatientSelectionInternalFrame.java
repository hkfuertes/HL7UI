package Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.ORUUtils;
import Model.Paciente;
import ca.uhn.hl7v2.model.v26.segment.*;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JList;

public class PatientSelectionInternalFrame extends JInternalFrame {
	private JList<PatientInternalFrame> list;
	private DefaultListModel<PatientInternalFrame> listmodel = new DefaultListModel<PatientInternalFrame>();
	
	private MouseAdapter adapter = new MouseAdapter(){
		public void mouseClicked(MouseEvent evt) {
	        if (evt.getClickCount() == 2) {
	            list.getSelectedValue().setVisible(true);
	            System.out.println(list.getSelectedValue());
	        }
	    }
	};

	public PatientSelectionInternalFrame() {
		super("Seleccion de Paciente", true, true, true, true);

		list = new JList<PatientInternalFrame>();
		list.setModel(listmodel);
		list.addMouseListener(adapter);
		getContentPane().add(list, BorderLayout.CENTER);

		this.setSize(new Dimension(400, 300));
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	}

	public void printMessage(ORU_R01 message) {
		Paciente paciente = ORUUtils.getPaciente(message);
		PatientInternalFrame pif = getWindow(paciente);
		if (pif == null) {
			pif = new PatientInternalFrame(paciente);
			getDesktopPane().add(pif);
			pif.setVisible(false);
			listmodel.addElement(pif);
			//list.invalidate();
		}
		pif.printMessage(message);
		
	}
	
	public PatientInternalFrame getWindow(Paciente paciente){
		for(int i=0; i< listmodel.size(); i++){
			if(listmodel.get(i).getPaciente().equals(paciente))
				return listmodel.get(i);
		}
		return null;
	}
}