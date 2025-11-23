package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private SessionController sessionController;
    private JTextField sessionIdField;

    public ControlPanel(SessionController controller) {
        this.sessionController = controller;

        setLayout(new FlowLayout());
        add(new JLabel("Session ID:"));

        sessionIdField = new JTextField(10);
        add(sessionIdField);

        JButton startBtn = new JButton("Start");
        JButton stopBtn = new JButton("Stop");

        add(startBtn);
        add(stopBtn);

        startBtn.addActionListener(e -> {
            String id = sessionIdField.getText();
            sessionController.startSession(id);
        });

        stopBtn.addActionListener(e -> {
            String id = sessionIdField.getText();
            sessionController.stopSession(id);
        });
    }
}
