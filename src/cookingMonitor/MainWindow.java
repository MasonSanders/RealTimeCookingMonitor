package cookingMonitor;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private DevicePanel devicePanel;
    private SessionPanel sessionPanel;
    private ControlPanel controlPanel;
    private GraphPanel graphPanel;

    private AppController appController;

    public MainWindow(AppController controller) {
        this.appController = controller;

        setTitle("Cooking Monitor");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Composition (UML diamonds)
        devicePanel = new DevicePanel(appController);
        sessionPanel = new SessionPanel(appController.getSessionController());
        controlPanel = new ControlPanel(appController.getSessionController());
        graphPanel = new GraphPanel();

        add(devicePanel, BorderLayout.WEST);
        add(sessionPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(graphPanel, BorderLayout.EAST);
    }

    public void showWindow() {
        setVisible(true);
    }

    public void updatePanels() {
        devicePanel.refreshDevices();
        sessionPanel.updateSessionInfo();
        graphPanel.updateGraph();
    }
}
