package Graphics;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import Main.DaemonThread;

import javax.swing.JProgressBar;

public class TCPClientInternalFrame extends JInternalFrame implements ActionListener{
	private JTextField addr;
	private JTextField port;
	private DaemonThread demonio;
	private JToggleButton startStop;
	private JProgressBar progressBar;
	
	private final String BUTTON_NAMES [] = {
			"Iniciar", "Parar"
	};

	public TCPClientInternalFrame(DaemonThread demonio){
		this();
		this.demonio = demonio;
	}
	
	private TCPClientInternalFrame() {
		super("Conexion Cliente TCP",false,true,true,true);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Direccion:");
		lblNewLabel.setBounds(6, 6, 69, 16);
		getContentPane().add(lblNewLabel);
		
		addr = new JTextField();
		addr.setBounds(76, 1, 186, 26);
		getContentPane().add(addr);
		addr.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setBounds(6, 34, 61, 16);
		getContentPane().add(lblPuerto);
		
		port = new JTextField();
		port.setBounds(76, 29, 69, 26);
		getContentPane().add(port);
		port.setColumns(10);
		
		startStop = new JToggleButton(BUTTON_NAMES[0]);
		startStop.setBounds(146, 29, 113, 29);
		startStop.addActionListener(this);
		getContentPane().add(startStop);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(6, 62, 256, 20);
		progressBar.setIndeterminate(true);
		progressBar.setEnabled(false);
		getContentPane().add(progressBar);
		setName("RawData");
		
		this.setSize(290, 145);
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(startStop.isSelected()){
			//arrancamos
			//System.out.println(addr.getText() + " " + Integer.parseInt(port.getText()));
			demonio.connectToServer(addr.getText(), Integer.parseInt(port.getText()));
			progressBar.setEnabled(true);
			startStop.setText(BUTTON_NAMES[1]);
		} else {
			//Paramos
			demonio.disconnectFromServer();
			progressBar.setEnabled(false);
			startStop.setText(BUTTON_NAMES[0]);
		}
		
	}
}
