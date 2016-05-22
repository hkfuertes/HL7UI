package Graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Model.Paciente;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;

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
	private Paciente paciente;

	public PatientInternalFrame(Paciente paciente) {
		super(paciente.toString(),false,true,false,true);
		
		this.paciente = paciente;

		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(6, 6, 61, 16);
		getContentPane().add(lblNombre);
		
		paciente_nombre = new JLabel(paciente.nombre + " " + paciente.apellidos);
		paciente_nombre.setBounds(65, 6, 361, 16);
		getContentPane().add(paciente_nombre);
		
		table = new JTable(testdata, columnNames);
		table.setBounds(6, 34, 464, 214);
		getContentPane().add(table);
		
		this.setSize(new Dimension(500, 300));
		this.setLocation(0, 0);
	}
	
	public void printMessage(ORU_R01 message){
		
	}
	
	public Paciente getPaciente(){
		return paciente;
	}
	
	public String toString(){
		return paciente.toString();
	}
}
