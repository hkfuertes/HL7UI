package Graphics;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextArea;

public class RawInternalFrame extends JInternalFrame {
	private JTextArea rawData;

	public RawInternalFrame() {
		super("Datos en Crudo",true,true,true,true);
		setLayout(new BorderLayout(0, 0));
		setName("RawData");
		
		rawData = new JTextArea();
		JScrollPane scrol = new JScrollPane(rawData);
		this.getContentPane().add(scrol, BorderLayout.CENTER);
		
		this.setSize(new Dimension(400,300));
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	}
	
	public void appendMessage(String message){
		this.rawData.append(message.replace("\r", "\n"));
	}

}
