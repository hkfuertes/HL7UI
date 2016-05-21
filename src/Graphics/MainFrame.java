package Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hkfuertes on 21/05/16.
 */
public class MainFrame extends JFrame implements ActionListener{

    private static final String DEFAULT_NAME = "HL7 Arduino Viewer";
    private JDesktopPane mainPane = new JDesktopPane();

    private JMenuBar menuBar ;

    public MainFrame(){
        this(DEFAULT_NAME);
    }

    private void createMenuBar(){
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem("Salir");
        item.addActionListener(this);
        menu.add(item);
    }

    public MainFrame(String title){
        super(title);
        setContentPane(mainPane);
        createMenuBar();
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
