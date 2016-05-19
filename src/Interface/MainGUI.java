package Interface;

import Model.HL7Message;
import Serial.HL7SerialConnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class MainGUI extends JFrame implements ActionListener, HL7SerialConnector.HL7Listener {
    private JTextArea text;
    private JPanel rootPanel;

    public MainGUI(String title) {
        super(title);

        init();

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
        HL7SerialConnector hl7SerialConnector = new HL7SerialConnector();
        hl7SerialConnector.setHL7Listener(this);
    }

    public void appendText(String text) {
        this.text.append(text + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("stop")) {

        }
    }

    @Override
    public void onMessageReceived(HL7Message message) {
        appendText(message.toString());
    }
}
