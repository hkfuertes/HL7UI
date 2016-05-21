package Graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;

public class PatientInternalFrame extends JInternalFrame {

	public JTextArea rawData;
	private JLabel paciente_nombre;
	private JTable table;
	
	String[] columnNames = {"Hora",
            "Temperatura",
            "Pulso",
            "Saturacion"};
	
	Object[][] testdata = {
		    {"19.00", new Float(37.1),new Integer(60), new Integer(98)},
		    {"19.00", new Float(37.1),new Integer(60), new Integer(98)},
		    {"19.00", new Float(37.1),new Integer(60), new Integer(98)},
		    {"19.00", new Float(37.1),new Integer(60), new Integer(98)},
		    {"19.00", new Float(37.1),new Integer(60), new Integer(98)}
		};

	public PatientInternalFrame(String paciente) {
		super(paciente,true,true,true,true);

		
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(6, 6, 61, 16);
		getContentPane().add(lblNombre);
		
		paciente_nombre = new JLabel("");
		paciente_nombre.setBounds(65, 6, 361, 16);
		getContentPane().add(paciente_nombre);
		
		JLabel lblEnddevice = new JLabel("END_DEVICE:");
		lblEnddevice.setBounds(6, 28, 83, 16);
		getContentPane().add(lblEnddevice);
		
		JLabel paciente_enddevice = new JLabel("");
		paciente_enddevice.setBounds(96, 28, 324, 16);
		getContentPane().add(paciente_enddevice);
		
		table = new JTable(testdata, columnNames);
		table.setBounds(6, 56, 414, 192);
		getContentPane().add(table);
	}
	
	public void printMessage(ca.uhn.hl7v2.model.v26.message.ORU_R01 message){
		
	}
}
