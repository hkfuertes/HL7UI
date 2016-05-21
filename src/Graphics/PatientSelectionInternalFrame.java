package Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v26.datatype.XPN;
import ca.uhn.hl7v2.model.v26.segment.PID;
import ca.uhn.hl7v2.util.Terser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JList;

public class PatientSelectionInternalFrame extends JInternalFrame implements ListSelectionListener {
	HashMap<String, PatientInternalFrame> pacientes = new HashMap<String, PatientInternalFrame>();
	private JList list;
	private DefaultListModel listModel;

	public PatientSelectionInternalFrame() {
		super("Seleccion de Paciente", true, true, true, true);

		list = new JList();
		list.addListSelectionListener(this);
		getContentPane().add(list, BorderLayout.CENTER);

		this.setSize(new Dimension(400, 300));
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	}

	public void printMessage(ca.uhn.hl7v2.model.v26.message.ORU_R01 message) {
		String paciente = getPatientName(message);
		if (!pacientes.containsKey(paciente)) {
			pacientes.put(paciente, new PatientInternalFrame(paciente));
			list.setListData(pacientes.keySet().toArray());
		}
		pacientes.get(paciente).printMessage(message);
		list.invalidate();
	}

	private String getPatientName(ca.uhn.hl7v2.model.v26.message.ORU_R01 msg) {
		Terser terser = new Terser(msg);
		String pname = "Jhon Doe";
		try {
			pname = terser.get("/.PID-5-2");
			pname += " " + terser.get("/.PID-5-1");
		} catch (HL7Exception e) {
			e.printStackTrace();
		}

		return pname;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

}