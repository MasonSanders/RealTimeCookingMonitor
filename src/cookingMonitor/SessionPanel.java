package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class SessionPanel extends JPanel {

    private SessionController sessionController;
    private JLabel sessionLabel;
    private JLabel tempLabel;
    private JLabel progressLabel;
    private JLabel timeRemainingLabel;

    public SessionPanel(SessionController sessionController) {
        this.sessionController = sessionController;

        setLayout(new GridLayout(4, 1));
        add(new JLabel("Session Information"));

        sessionLabel = new JLabel("Session ID: None");
        tempLabel = new JLabel("Current Temp: --");
        progressLabel = new JLabel("Progress: --");
        timeRemainingLabel = new JLabel("Time Remaining: --");

        add(sessionLabel);
        add(tempLabel);
        add(progressLabel);
        add(timeRemainingLabel);
    }

    public void updateSessionInfo() {
        // Simple example for one session
        if (sessionController.getSessions().isEmpty()) return;

        CookingSession session = sessionController.getSessions()
                .values()
                .iterator()
                .next();

        sessionLabel.setText("Session: " + session.getSessionID());
        tempLabel.setText("Current Temp: " + session.getTimeRemaining());
        progressLabel.setText("Progress: " + session.getProgress());
        timeRemainingLabel.setText("Time Remaining: " + session.getTimeRemaining());
    }
}
