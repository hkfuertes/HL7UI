package Graphics;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import Main.DaemonThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Created by hkfuertes on 21/05/16.
 */
public class MainFrame extends JFrame implements ActionListener{

    private static final String DEFAULT_NAME = "HL7 Arduino Viewer";
    private JPanel mainPane;

    private JMenuBar menuBar ;
    private JDesktopPane desktopPane;
    
    private RawInternalFrame rawData = new RawInternalFrame();
    private TCPClientInternalFrame tcpFrame = null;
    private PatientSelectionInternalFrame psif = new PatientSelectionInternalFrame();
    
    private JToolBar toolBar;
    private JButton rawBtn, tcpBtn;

    public MainFrame(){
        this(DEFAULT_NAME);
    }

    private void createMenuBar(){
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem("Salir");
        item.setActionCommand("exit");
        item.addActionListener(this);
        menu.add(item);
    }

    public MainFrame(String title){
        super(title);
        initComponents();
        initToolbar();
        setContentPane(mainPane);
        setJMenuBar(menuBar);
        setMinimumSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initToolbar(){
    	toolBar = new JToolBar();
        mainPane.add(toolBar, BorderLayout.NORTH);
        
        rawBtn = new JButton("RAW");
        rawBtn.setActionCommand("raw");
        rawBtn.addActionListener(this);
        
        plist = new JButton("Lista");
        plist.setActionCommand("list");
        plist.addActionListener(this);
        
        toolBar.add(plist);
        toolBar.add(rawBtn);
        
        tcpBtn = new JButton("TCP Client");
        tcpBtn.setActionCommand("tcp_client");
        tcpBtn.addActionListener(this);
        toolBar.add(tcpBtn);
    }
    
    private void initComponents(){
    	createMenuBar();
    	mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout(0, 0));
        
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.LIGHT_GRAY);
        
        //Anadimos frames al panel
        desktopPane.add(rawData);
        desktopPane.add(psif);
        
        mainPane.add(desktopPane, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals("raw")){
    		rawData.setVisible(true);
    	}else if(e.getActionCommand().equals("tcp_client")){
    		if(tcpFrame != null){
    			tcpFrame.setVisible(true);
    		}
    		
    	}else if(e.getActionCommand().equals("list")){
    		psif.setVisible(true);
    		
    	}else if(e.getActionCommand().equals("exit")){
    		System.exit(0);
    	}
        
    }
    
    public void printMessage(ca.uhn.hl7v2.model.v26.message.ORU_R01 msg){
    	rawData.rawData.append(msg.toString());
    	psif.printMessage(msg);
    }
    
    DaemonThread demonio;
    private JButton plist;
    public void registerDaemon(DaemonThread demonio){
    	this.demonio = demonio;
    	
    	//Creamos panel y lo añadimos
    	this.tcpFrame = new TCPClientInternalFrame( demonio );
    	desktopPane.add(tcpFrame);
    }
}
